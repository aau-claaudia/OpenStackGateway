package dk.aau.claaudia.openstackgateway.services

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dk.aau.claaudia.openstackgateway.config.Messages
import dk.aau.claaudia.openstackgateway.config.OpenStackProperties
import dk.aau.claaudia.openstackgateway.config.ProviderProperties
import dk.aau.claaudia.openstackgateway.extensions.getLogger
import dk.aau.claaudia.openstackgateway.extensions.oldOpenstackName
import dk.aau.claaudia.openstackgateway.extensions.openstackName
import dk.aau.claaudia.openstackgateway.extensions.ucloudId
import dk.aau.claaudia.openstackgateway.models.StackStatus
import dk.sdu.cloud.CommonErrorMessage
import dk.sdu.cloud.PageV2
import dk.sdu.cloud.accounting.api.Product
import dk.sdu.cloud.accounting.api.ProductPriceUnit
import dk.sdu.cloud.accounting.api.providers.ResourceBrowseRequest
import dk.sdu.cloud.accounting.api.providers.ResourceChargeCredits
import dk.sdu.cloud.accounting.api.providers.ResourceRetrieveRequest
import dk.sdu.cloud.app.orchestrator.api.*
import dk.sdu.cloud.app.store.api.AppParameterValue
import dk.sdu.cloud.calls.BulkRequest
import dk.sdu.cloud.calls.client.IngoingCallResponse
import dk.sdu.cloud.calls.client.orThrow
import dk.sdu.cloud.provider.api.ResourceUpdateAndId
import dk.sdu.cloud.providers.UCloudClient
import dk.sdu.cloud.providers.call
import org.openstack4j.api.Builders
import org.openstack4j.api.OSClient.OSClientV3
import org.openstack4j.model.common.Identifier
import org.openstack4j.model.compute.Flavor
import org.openstack4j.model.compute.Server
import org.openstack4j.model.compute.VNCConsole
import org.openstack4j.model.heat.Event
import org.openstack4j.model.heat.Stack
import org.openstack4j.model.heat.Template
import org.openstack4j.model.identity.v3.Token
import org.openstack4j.model.image.v2.Image
import org.openstack4j.model.network.SecurityGroup
import org.openstack4j.model.storage.block.Volume
import org.openstack4j.model.storage.block.VolumeAttachment
import org.openstack4j.openstack.OSFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.text.MessageFormat
import java.time.Duration
import java.time.Instant
import java.util.*
import java.util.concurrent.Executors

@Service
class OpenStackService(
    private val config: OpenStackProperties,
    private val provider: ProviderProperties,
    private val messages: Messages,
    private val templateService: TemplateService,
    private val uCloudClient: UCloudClient
) {
    private val domainIdentifier = Identifier.byId("default")
    private val projectIdentifier = Identifier.byId(config.project.id)
    private var token: Token? = null
    private fun Token.hasExpired(): Boolean = expires.before(Date())

    private val threadPool = Executors.newCachedThreadPool()

    /**
     * Handle openstack authentication.
     * Returns an openstack client that is used for all openstack communication
     * Uses the auth endpoint, projectId, username and password from config
     *
     * A token is cached and used to build a client on subsequent requests
     * If the token has expired, authenticate to get a new one.
     */
    private fun getClient(): OSClientV3 {
        //OSFactory.enableHttpLoggingFilter(true)
        if (token?.hasExpired() == false) {
            logger.info("Using existing openstack token")
            return OSFactory.clientFromToken(token)
        } else {
            logger.info("Getting new openstack token")
            //Create and saving client for future use
            val client = OSFactory.builderV3()
                .endpoint(config.endpoints.auth)
                .credentials(
                    config.username,
                    config.password,
                    domainIdentifier
                )
                .scopeToProject(projectIdentifier)
                .authenticate()
            logger.info("Saving openstack token")
            token = client.token
            return client
        }
    }

    fun listServers(): List<Server?> {
        return getClient().compute().servers().list()
    }

    fun listImages(): List<Image?> {
        val client = getClient()
        logger.info("client, $client")
        return client.imagesV2().list()
    }

    fun getImage(id: String?): Image? {
        val image = getClient().imagesV2().get(id)
        logger.info("image: $image")
        return image
    }

    fun getSecurityGroup(id: String?): SecurityGroup? {
        val group = getClient().networking().securitygroup().get(id)
        logger.info("Security group: $group")
        return group
    }

    fun listFlavors(): List<Flavor?> {
        val list = getClient().compute().flavors().list()
        logger.info("list flavors:", list)
        return list
    }

    fun getFlavorExtraSpecs(id: String?): MutableMap<String, String> {
        return getClient().compute().flavors().listExtraSpecs(id)
    }

    fun getFlavorById(id: String?): Flavor? {
        val flavor = getClient().compute().flavors().get(id)
        logger.info("flavor by id: $flavor")
        return flavor
    }

    fun getFlavorByName(name: String?): Flavor? {
        val flavor = getClient().compute().flavors().list().first { it.name == name }
        logger.info("flavor by name: $flavor")
        return flavor
    }

    fun getStackByJob(job: Job): Stack? {
        val client = getClient()
        // UCloud have changed the JobIds from UUIDs to integers.
        // The old Id should still be on the job and is used to retrieve the stack
        val newIdStack = client.heat().stacks().getStackByName(job.openstackName)
        if (newIdStack == null) {
            logger.info("Could not find stack from id: ${job.openstackName}. Try old id ${job.providerGeneratedId} ${job.oldOpenstackName}")
            val oldIdStack = client.heat().stacks().getStackByName(job.oldOpenstackName)
            if (oldIdStack != null) {
                logger.info("Found: ${oldIdStack.name}")
                return oldIdStack
            }
        }
        return newIdStack
    }

    /**
     * Given a ucloud application name and version find the corresponding openstack image
     * Since ucloud uses name and version and not a unique name/id
     * we use this config mapping instead of a strict naming convention of images
     * The config is also used to control which images are available
     */
    fun mapImage(name: String, version: String): String {
        val image = provider.images.firstOrNull { it.ucloudName == name && it.ucloudVersion == version }

        return when {
            image?.openstackId?.isNotBlank() == true -> {
                image.openstackId
            }
            image?.openstackName?.isNotBlank() == true -> {
                image.openstackName
            }
            else -> {
                ""
            }
        }
    }

    /**
     * Prepare parameters for the stack template
     * This parses ucloud application properties to a map that matches parameters in the stack template
     * For now, there are some hardcoded parameters since we only use a single template
     *
     */
    fun prepareParameters(job: Job): MutableMap<String, String> {
        val parameters = mutableMapOf<String, String>()

        parameters["image"] = mapImage(job.specification.application.name, job.specification.application.version)
        parameters["flavor"] = job.specification.product.id

        // FIXME Handle all cases here. Consider handling these with json objects?
        for (parameter in job.specification.parameters!!) {
            val v = when (val value = parameter.value) {
                is AppParameterValue.Text -> value.value
                is AppParameterValue.File -> TODO()
                is AppParameterValue.Bool -> TODO()
                is AppParameterValue.Integer -> TODO()
                is AppParameterValue.FloatingPoint -> TODO()
                is AppParameterValue.Peer -> TODO()
                is AppParameterValue.License -> TODO()
                is AppParameterValue.BlockStorage -> TODO()
                is AppParameterValue.Network -> TODO()
                is AppParameterValue.Ingress -> TODO()
                else -> null
            }
            parameters[parameter.key] = v as String
        }

        parameters["network"] = config.network
        parameters["security_group"] = config.securityGroup
        parameters["key_name"] = config.keyName

        return parameters
    }

    /**
     * Start the process of creating stacks from ucloud jobs
     * For now, we use a single template, but this can change in the future.
     * For each job start an asynchronous task
     */
    fun createStacks(jobs: List<Job>) {
        logger.info("creating stacks: $jobs.size")

        // There is only one template for now
        val template = templateService.getTemplate("ucloud")

        for (job in jobs) {
            // Starts a thread for each job
            asyncStackCreation(job, template)
        }
    }

    /**
     * Parse parameters from the ucloud job, send parameters and template to openstack in a createStack request
     *
     * Verification is done in order to detect missing required template parameters
     * Verify that flavor, image and security group exists in openstack
     *
     * If anything is missing, send a status update to ucloud with a job failed status.
     *
     * @property template the openstack stack template to create a stack from
     * @property job the ucloud job to get parameters from
     */
    fun asyncStackCreation(job: Job, template: Template) {
        threadPool.execute {
            // Try to create the stack and report any errors to ucloud
            try {
                val parameters = prepareParameters(job)

                // Verify template required parameters are present
                val missingParameters: List<String> = templateService.findMissingParameters(template, parameters)
                if (missingParameters.isNotEmpty()) {
                    logger.error("Received parameters: $parameters")
                    logger.error("Excepted parameters: ${template.templateJson}")
                    logger.error("Missing parameters: $missingParameters")

                    sendJobFailedMessage(job.id, "Missing parameters: $missingParameters")
                    return@execute
                }

                // Verify flavor exists
                getFlavorByName(parameters["flavor"]) ?: run {
                    val errorMessage = "Flavor not found: ${parameters["flavor"]}"
                    logger.error(errorMessage)
                    sendJobFailedMessage(job.id, errorMessage)
                    return@execute
                }

                // Verify image exists
                getImage(parameters["image"]) ?: run {
                    val errorMessage = "Image not found: ${parameters["image"]}"
                    logger.error(errorMessage)
                    sendJobFailedMessage(job.id, errorMessage)
                    return@execute
                }

                // Verify security group exists
                getSecurityGroup(parameters["security_group"]) ?: run {
                    val errorMessage = "Security group not found: ${parameters["security_group"]}"
                    logger.error(errorMessage)
                    sendJobFailedMessage(job.id, errorMessage)
                    return@execute
                }

                // Send stack creation to openstack
                createStack(job.openstackName, template.templateJson, parameters)
            } catch (e: Exception) {
                // Not really nice to catch all exceptions here is it...

                logger.error("Exception $e.message")
                sendJobFailedMessage(job.id, e.message as String)

                // Raise exception for logging
                throw e
            }

            return@execute
        }
    }

    /**
     * Send a stack creation request to openstack.
     * Use the openstack4j builder to create the request and send it to openstack
     * We don't return anything here because, since tasks are started to monitor the creation.
     *
     * @property name the name of the stack to be created
     * @property template openstack stack template to be used
     * @property parameters the stack parameters for the template
     */
    fun createStack(name: String, template: String, parameters: MutableMap<String, String>) {
        logger.info("Create stack: $name", template, parameters)

        val client = getClient()

        val build = Builders.stack()
            .template(template)
            .parameters(parameters)
            .name(name)
            .timeoutMins(60)
            .build()

        // TODO Consider passing parameters as a file
        //Builders.stack().files(mutableMapOf("file1" to "file contents"))

        client.heat().stacks().create(build)
    }

    /**
     * Monitor a list of jobs and send status to ucloud when jobs are running or failed
     *
     * For each job start an async task that polls openstack for the corresponding stack
     * Based on the config, have a delay between each poll and a timeout to stop polling.
     * When the job is found in a create complete state, send the output IP to ucloud in a job success update
     * If this IP is not found or the stack isnt created within the timeout, send a job failed update
     */
    fun sendStatusWhenStackComplete(jobs: List<Job>) {
        Thread.sleep(5000) // TODO Status is not set if we change this too quickly (https://github.com/SDU-eScience/UCloud/issues/2303)

        for (job in jobs) {
            threadPool.execute {
                val startTime = System.currentTimeMillis()
                while (startTime + config.monitor.timeout > System.currentTimeMillis()) {
                    val stack = getStackByJob(job)
                    logger.info("Monitoring stack status", stack, stack?.status)
                    if (stack != null && stack.status == StackStatus.CREATE_COMPLETE.name) {
                        logger.info("Stack CREATE complete")
                        val outputIP = stack.outputs.find { it["output_key"] == "server_ip" }
                        if (!outputIP.isNullOrEmpty()) {
                            sendJobRunningMessage(job.id, outputIP["output_value"] as String)
                        } else {
                            logger.error("Did not receive IP output from openstack", job, stack)
                            sendJobFailedMessage(job.id, "Did not receive IP output from openstack")
                        }

                        return@execute
                    }
                    if (stack != null && stack.status == StackStatus.CREATE_FAILED.name) {
                        logger.error("Stack create failed", job, stack)
                        sendJobFailedMessage(job.id, "Job failed. Reason: ${stack.stackStatusReason}")
                        return@execute
                    }
                    // Sleep until next retry
                    logger.info("Waiting to retry")
                    Thread.sleep(config.monitor.delay)
                }
                // Job could not be started within the timeout
                logger.info("Could not start job within timeout: $job.id")
                return@execute
            }
        }
    }

    /**
     * A shortcut function for sending job failed update message
     */
    fun sendJobFailedMessage(jobId: String, message: String) {
        sendJobStatusMessage(
            jobId,
            JobState.FAILURE,
            MessageFormat.format(messages.jobs.createFailed, message)
        )
    }

    /**
     * A shortcut function for sending job success update message
     */
    fun sendJobRunningMessage(jobId: String, message: String) {
        sendJobStatusMessage(
            jobId,
            JobState.RUNNING,
            MessageFormat.format(messages.jobs.createComplete, message)
        )
    }

    /**
     * This sends a JobsControlUpdateRequest to ucloud.
     * @property jobId the ucloud jobid
     * @property state the ucloud JobState the job should have
     * @property message a custom message that is shown in ucloud on the job page
     */
    fun sendJobStatusMessage(jobId: String, state: JobState, message: String) {
        JobsControl.update.call(
            BulkRequest(
                listOf(
                    ResourceUpdateAndId(
                        jobId,
                        JobUpdate(state = state, status = message)
                    )
                )
            ),
            uCloudClient
        ).orThrow()
    }

    /**
     * For each stack in openstack, charge the corresponding job in ucloud.
     */
    fun chargeAllStacks() {
        val client = getClient()

        val activeStacks = client.heat().stacks().list().filter {
            it.status in listOf(
                StackStatus.CREATE_COMPLETE.name,
                StackStatus.UPDATE_COMPLETE.name,
                StackStatus.UPDATE_FAILED.name
            )
        }
        logger.info("list: ${activeStacks.size}")

        activeStacks.forEach {
            asyncChargeJob(it)
        }
    }

    /**
     * Charge a stack asynchronous to avoid exceptions stopping other stacks from being charged
     */
    fun asyncChargeJob(stack: Stack) {
        threadPool.execute {
            chargeStack(stack)
            return@execute
        }
    }

    /**
     * Charge stack by calling JobControl.chargeCredits
     *
     * Get the corresponding job from ucloud to make sure it exists, return if not
     *
     * Calculate the time amount to charge.
     * This amount is the time between the lastCharged timestamp and now.
     *
     * Maintain lastCharged for each stack as a tag on the stack in openstack.
     * lastCharged is the moment the stack was charged last.
     * The first time a stack is charged, use its creation time as lastCharged
     *
     * @property stack the stack to charge
     */
    fun chargeStack(stack: Stack) {
        val job: Job?
        // Try to find the UCloud job using the stack name
        val jobRetrieve: IngoingCallResponse<Job, CommonErrorMessage> = JobsControl.retrieve.call(
            ResourceRetrieveRequest(
                JobIncludeFlags(includeProduct = true),
                stack.ucloudId
            ),
            uCloudClient
        )

        if (jobRetrieve is IngoingCallResponse.Ok) {
            job = jobRetrieve.result
        } else {
            logger.info("Couldn't find ucloud job by id. Trying old providerId. UcloudId: ${stack.ucloudId}")
            val jobs: PageV2<Job> = JobsControl.browse.call(
                ResourceBrowseRequest(
                    JobIncludeFlags(
                        filterProviderIds = stack.ucloudId,
                        includeProduct = true
                    )
                ),
                uCloudClient
            ).orThrow()
            if (jobs.items.size == 1) {
                job = jobs.items.first()
                logger.info("Found job in UCloud from old providerId. stackUCloudId: ${stack.ucloudId}: UCloudid: ${job.id}")
            } else {
                logger.error("Could not charge job. Could not find job in UCloud: ${stack.ucloudId}")
                return
            }
        }


        logger.info("Charge. Found ucloud job with id: ${job.id} Owner: ${job.owner.createdBy}")

        val chargeTime: Instant = Instant.now()
        val lastChargedTime: Instant = getLastChargedFromStack(stack)

        val duration = Duration.between(lastChargedTime, chargeTime)

        val product: Product.Compute? = job.status.resolvedProduct

        if (product == null) {
            logger.error("Could not charge job. Job had not resolved product. UcloudId: ${stack.ucloudId}")
            return
        }

        val cpuCores: Int? = product.cpu

        if (cpuCores == null) {
            logger.error("Could not charge job. Product had no cpu count. UcloudId: ${stack.ucloudId}")
            return
        }

        //        Units is the number of cores cores
        //        Period is the time spent.
        //        - This is counted in minutes/hours or days depending on the products unitOfPrice

        var period: Long = 0
        if (product.unitOfPrice == ProductPriceUnit.CREDITS_PER_HOUR) {
            period = duration.toHours()
            if (period == 0L) {
                logger.error("It makes no sense to charge zero hours. UcloudId: ${stack.ucloudId}")
                return
            }
        } else if (product.unitOfPrice == ProductPriceUnit.CREDITS_PER_MINUTE) {
            period = duration.toMinutes()
            if (period == 0L) {
                logger.error("It makes no sense to charge zero minutes. UcloudId: ${stack.ucloudId}")
                return
            }
        }

        logger.info("Will now charge: ${job.id} $lastChargedTime ${cpuCores.toLong()} $period")
        val response = JobsControl.chargeCredits.call(
            BulkRequest(
                listOf(
                    ResourceChargeCredits(
                        job.id,
                        lastChargedTime.toString(), // Er chargedate okay som chargeId
                        cpuCores.toLong(),
                        period
                    )
                )
            ),
            uCloudClient
        ).orThrow()

        if (response.duplicateCharges.isEmpty() && response.insufficientFunds.isEmpty()) {
            // Everything is good
            logger.info("Stack ${stack.name}. ucloud job was charged for $cpuCores cores for period $period. $response")
            updateStackLastCharged(stack, chargeTime)
        } else if (response.insufficientFunds.isNotEmpty()) {
            // Insufficient funds
            logger.info("Stack ${stack.name}. ucloud job has insufficient funds. $response")
            // TODO Shelve job here?
        } else if (response.duplicateCharges.isNotEmpty()) {
            // Duplicate charges
            logger.info("Stack ${stack.name}. ucloud job duplicate charges. $response")
            updateStackLastCharged(stack, chargeTime)
        }
        logger.info("Charge response:", response)
    }

    /**
     * Retrieve lastCharged timestamp from stack in openstack
     *
     * The timestamp is saved as a text tag on the stack and includes a prefix
     * Get this timestamp, remove prefix and parse to Instant
     * If timestamp not found, return creation timestamp from stack
     */
    fun getLastChargedFromStack(stack: Stack): Instant {
        // TODO Consider storing lastcharged in database not openstack tags

        val prefix = "lastcharged:"
        val lastCharged = stack.tags?.first { it.contains(prefix) }?.removePrefix(prefix)

        return if (lastCharged.isNullOrBlank()) {
            Instant.parse(stack.creationTime)
        } else {
            // Hope this parses
            Instant.parse(lastCharged)
        }
    }

    /**
     * Update the lastCharged timestamp on a stack
     *
     * The process is rather convoluted and subject to change.
     * In order to change the tag we have to do a stack update.
     * This requires the stack template.
     *
     * Retrieve the template and remove the already saved ids.
     * Create a stackUpdate and include the updated tag and the existing template and parameters
     */
    fun updateStackLastCharged(listStack: Stack, chargedAt: Instant) {
        // FIXME Store in database not openstack tags
        // Alternatively: Store on metadata on instance

        //Start by getting the stack with all details.
        //When stack is retrieved from list it doesn't have parameters

        val client = getClient()
        val stack = client.heat().stacks().getStackByName(listStack.name)
        if (stack == null) {
            logger.error("Could not update lastcharged. Stack not found: $listStack.name")
            return
        }

        val templateAsMap = client.heat().templates().getTemplateAsMap(stack.name)
        val jsonStr = jacksonObjectMapper().writeValueAsString(templateAsMap)

        stack.parameters.remove("OS::stack_id")
        stack.parameters.remove("OS::stack_name")
        stack.parameters.remove("OS::project_id")

        val stackUpdate = Builders.stackUpdate()
            .tags("lastcharged:$chargedAt")
            .template(jsonStr)
            .parameters(stack.parameters)
            .build()

        val update = client.heat().stacks().update(stack.name, stack.id, stackUpdate)

        if (update.isSuccess) {
            logger.info("Stack lastcharged timestamp updated", stack)
        } else {
            logger.error("Stack lastcharged timestamp could no be updated", stack)
        }

    }

    fun getStackEvents(stackName: String, stackIdentity: String): MutableList<out Event>? {
        val client = getClient()
        return client.heat().events().list(stackName, stackIdentity)
    }

    fun chargeDeleteJobs(jobs: List<Job>) {
        for (job in jobs) {
            asyncChargeDeleteJob(job)
        }
    }

    fun asyncChargeDeleteJob(job: Job) {
        threadPool.execute {
            val stack = getStackByJob(job)
            if (stack != null) {
                logger.error("AsyncChargeDeleteJob could not delete. Job: ${job.id} Stack: ${job.openstackName}")
                chargeStack(stack)
                deleteJob(job)
            }
            return@execute
        }
    }

    fun deleteJob(job: Job) {
        val client = getClient()

        val stackByName = getStackByJob(job)
        if (stackByName != null) {
            logger.info("Deleting stack: ${stackByName.name}")
            val delete = client.heat().stacks().delete(stackByName.name, stackByName.id)
            if (!delete.isSuccess) {
                logger.error("Stack deletion request was unsuccessful ${stackByName.name}")
            } else {
                logger.error("Stack deletion request was successful ${stackByName.name}")
            }
        } else {
            logger.info("Stack not found with name: ${job.openstackName}")
        }
    }

    fun monitorStackDeletions(jobs: List<Job>) {
        Thread.sleep(5000) // TODO Status is not set if we change this too quickly (https://github.com/SDU-eScience/UCloud/issues/2303)

        for (job in jobs) {
            threadPool.execute {
                val startTime = System.currentTimeMillis()
                while (startTime + config.monitor.timeout > System.currentTimeMillis()) {
                    val stack = getStackByJob(job)
                    if (stack == null) {
                        logger.info("Could not find stack. Assume deleted", job.id)
                        sendJobStatusMessage(job.id, JobState.SUCCESS, "Stack DELETE complete")
                        return@execute

                    }
                    logger.info("Stack was found and not yet deleted", stack)
                    // Sleep until next retry
                    logger.info("Waiting to retry")
                    Thread.sleep(config.monitor.delay)
                }
                // Job could not be deleted
                logger.error("Job could no be deleted", job)
                // Dont think we should send a status update here because the user should
                // be able to retry the delete
                //sendJobFailedMessage(job.id, "Could not delete job. Timeout exceeded")
                return@execute
            }
        }
    }

    fun verifyJobs(jobs: List<Job>) {
        for (job in jobs) {
            verifyJob(job)
        }
    }

    /**
     * Verify a jobs status matches the status of the corresponding stack in openstack
     * If no stack found in openstack, assume deleted and send status update to ucloud
     *
     * The function contains a map statusMappings that maps the openstack statuses to the ucloud job statuses
     * If the stack status is different than the expected send an status update to ucloud.
     */
    fun verifyJob(job: Job) {
        val stack = getStackByJob(job)

        if (stack == null) {
            logger.info("Could not find stack. Assume nothing", job, stack)
            //sendJobStatusMessage(job.id, JobState.SUCCESS, "Stack was deleted")
            return
        }

        // Move this to config?
        val statusMappings = mapOf<StackStatus, JobState>(
            StackStatus.CREATE_COMPLETE to JobState.RUNNING,
            StackStatus.UPDATE_COMPLETE to JobState.RUNNING,
            StackStatus.CREATE_IN_PROGRESS to JobState.IN_QUEUE,
            StackStatus.CREATE_FAILED to JobState.FAILURE
        )

        val expectedJobState = statusMappings[StackStatus.valueOf(stack.status)]

        when {
            expectedJobState == null -> {
                // StatusMappings should be expanded to avoid this
                logger.info("Unhandled status: ${stack.status}", job, stack)
                sendJobStatusMessage(
                    job.id, JobState.RUNNING,
                    "ERROR, Unknown status: ${job.status.state.name}"
                )
            }
            job.status.state != expectedJobState -> {
                // Status in ucloud is not as expected. Send update.
                logger.info("Job status not as expected, updating ucloud: ${stack.status}", job, stack)
                sendJobStatusMessage(job.id, expectedJobState, "Stack status: ${stack.status}")
            }
            else -> {
                logger.info("Status verified: ${stack.status}", job, stack)
            }
        }
    }

    fun removeFailedJobs() {
        //TODO Finish implementing this
        // For now, log failed jobs
        val client = getClient()

        val failedStacks = client.heat().stacks().list().filter {
            it.status in listOf(
                StackStatus.CREATE_FAILED.name,
                StackStatus.RESUME_FAILED.name,
                StackStatus.DELETE_FAILED.name
            )
        }
        logger.info("Found ${failedStacks.size} that should be deleted")
        for (failedStack in failedStacks) {
            logger.info("Stack: ${failedStack.id} - ${failedStack.stackStatusReason}")
        }
    }

    fun listVolumes(): List<Volume> {
        val list = getClient().blockStorage().volumes().list()
        logger.info("found volumes, $list")
        return list
    }

    fun createVolume(name: String, description: String, size: Int) {
        val volume = getClient().blockStorage().volumes()
            .create(
                Builders.volume()
                    .name(name)
                    .description(description)
                    .size(size)
                    .build()
            )
    }

    fun deleteVolume(id: String) {
        getClient().blockStorage().volumes().delete(id)
    }

    fun attackVolumeToInstance(instance: Server, volume: Volume) {
        getClient().compute().servers().attachVolume(instance.id, volume.id, "/dev/vdb")
    }

    fun deattachVolumeToInstance(instance: Server, volume: Volume) {
        // FIXME Disse fejl skal vel boble ud og fanges et andet sted?
        // Det giver vist ikke helt mening at smide httpstatus beskeder inde i servicen?
        val volumeAttachment: VolumeAttachment = volume.attachments.find { it.serverId == instance.id }
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Volume not found")
        getClient().compute().servers().detachVolume(instance.id, volumeAttachment.attachmentId)
    }

    fun getConsole(serverId: String): VNCConsole? {
        val client = getClient()

        return client.compute().servers().getVNCConsole(serverId, VNCConsole.Type.NOVNC)
    }

    companion object {
        val logger = getLogger()
    }
}