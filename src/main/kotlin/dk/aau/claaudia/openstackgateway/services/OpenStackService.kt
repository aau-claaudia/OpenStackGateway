package dk.aau.claaudia.openstackgateway.services

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dk.aau.claaudia.openstackgateway.config.Messages
import dk.aau.claaudia.openstackgateway.config.OpenStackProperties
import dk.aau.claaudia.openstackgateway.config.ProviderProperties
import dk.aau.claaudia.openstackgateway.extensions.getLogger
import dk.aau.claaudia.openstackgateway.extensions.oldOpenstackName
import dk.aau.claaudia.openstackgateway.extensions.openstackName
import dk.aau.claaudia.openstackgateway.extensions.ucloudId
import dk.aau.claaudia.openstackgateway.models.InstanceStatus
import dk.aau.claaudia.openstackgateway.models.StackStatus
import dk.sdu.cloud.CommonErrorMessage
import dk.sdu.cloud.FindByStringId
import dk.sdu.cloud.PageV2
import dk.sdu.cloud.accounting.api.Product
import dk.sdu.cloud.accounting.api.ProductPriceUnit
import dk.sdu.cloud.accounting.api.ProductReference
import dk.sdu.cloud.accounting.api.providers.ResourceBrowseRequest
import dk.sdu.cloud.accounting.api.providers.ResourceChargeCredits
import dk.sdu.cloud.accounting.api.providers.ResourceChargeCreditsResponse
import dk.sdu.cloud.accounting.api.providers.ResourceRetrieveRequest
import dk.sdu.cloud.app.orchestrator.api.*
import dk.sdu.cloud.app.store.api.AppParameterValue
import dk.sdu.cloud.calls.BulkRequest
import dk.sdu.cloud.calls.client.IngoingCallResponse
import dk.sdu.cloud.calls.client.orThrow
import dk.sdu.cloud.provider.api.ResourceUpdateAndId
import dk.sdu.cloud.providers.UCloudClient
import dk.sdu.cloud.providers.call
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.openstack4j.api.Builders
import org.openstack4j.api.OSClient.OSClientV3
import org.openstack4j.api.exceptions.ConnectionException
import org.openstack4j.api.exceptions.ResponseException
import org.openstack4j.api.exceptions.ServerResponseException
import org.openstack4j.core.transport.Config
import org.openstack4j.model.common.Identifier
import org.openstack4j.model.compute.Action
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
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.text.MessageFormat
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor

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

    private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default)

    fun monitorThreads() {
        logger.info("Monitor threads:")
        if (threadPool is ThreadPoolExecutor) {
            logger.info("Pool size    : ${threadPool.poolSize}")
            logger.info("Max pool size: ${threadPool.largestPoolSize}")
            logger.info("Active count : ${threadPool.activeCount}")
            logger.info("Task Count   : ${threadPool.taskCount}")
            logger.info("Queue size   : ${threadPool.queue.size}")
        }
    }

    /**
     * Handle openstack authentication.
     * Returns an openstack client that is used for all openstack communication
     * Uses the auth endpoint, projectId, username and password from config
     *
     * A token is cached and used to build a client on subsequent requests
     * If the token has expired, authenticate to get a new one.
     */
    private fun getClient(): OSClientV3 {
        val clientConfig = Config.newConfig()
            .withConnectionTimeout(60000)
            .withReadTimeout(60000)
        if (token?.hasExpired() == false) {
            return OSFactory.clientFromToken(token, clientConfig)
        } else {
            logger.info("Getting new openstack token")
            //Create and saving client for future use
            val client = OSFactory.builderV3()
                .withConfig(clientConfig)
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

    fun listImages(filterParams: Map<String, String>): List<Image?> {
        val client = getClient()
        logger.info("client, $client")
        return client.imagesV2().list(filterParams)
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
        val list = getClient().compute().flavors().list(true)
        logger.info("Getting flavors from openstack: ${list.size}")
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
        val flavor = getClient().compute().flavors().list().firstOrNull { it.name == name }
        logger.info("flavor by name: $flavor")
        return flavor
    }

    @Cacheable("products")
    fun retrieveProducts(): List<ComputeSupport> {
        return listFlavors().filterNotNull().map { flavor ->
            ComputeSupport(
                ProductReference(
                    flavor.name,
                    getFlavorExtraSpecs(flavor.id).getOrDefault(
                        "availability_zone", provider.defaultProductCategory
                    ).plus(if (flavor.name.endsWith("-h")) "-h" else ""),
                    provider.id
                ),
                ComputeSupport.Docker(
                    enabled = false,
                    web = false,
                    vnc = false,
                    logs = false,
                    terminal = false,
                    peers = false,
                    timeExtension = false,
                    utilization = false
                ),
                ComputeSupport.VirtualMachine(
                    enabled = true,
                    logs = false,
                    vnc = false,
                    terminal = false,
                    suspension = false,
                    timeExtension = false,
                    utilization = false
                )
            )
        }
    }

    fun findStackIncludeDeleted(job: Job): Stack? {
        val client = getClient()
        val stacks = client.heat().stacks().list(
            mapOf(
                "show_deleted" to "True",
                "name" to job.openstackName
            )
        )

        return stacks.first()
    }

    /**
     * Retrieve corresponding stack in openstack of a UCloud job.
     *
     * The stack name is the ucloud id prefixed based on config.
     */
    fun getStackByJob(job: Job, includeDeleted: Boolean = false): Stack? {
        val client = getClient()
        // UCloud have changed the JobIds from UUIDs to integers.
        // The old Id should still be on the job and is used to retrieve the stack
        val newIdStack = client.heat().stacks().getStackByName(job.openstackName)
        if (newIdStack == null) {
            // FIXME Remove when stacks with old ids are deleted.
            logger.info("Could not find stack from name: ${job.openstackName}. Try old name ${job.providerGeneratedId} ${job.oldOpenstackName}")
            val oldIdStack = client.heat().stacks().getStackByName(job.oldOpenstackName)
            if (oldIdStack != null) {
                logger.info("Found: ${oldIdStack.name}")
                return oldIdStack
            }

            if (includeDeleted) {
                //Try to find the deleted stack from name
                return client.heat().stacks().list(
                    mapOf("show_deleted" to "true", "name" to job.openstackName)
                ).first()
            }

        }
        return newIdStack
    }

    /**
     * Wrapper function to make mocking possible in unit testing
     */
    fun getStackByName(stackName: String): Stack?{
        val client = getClient()
        return client.heat().stacks().getStackByName(stackName)
    }

    /**
     * Given a ucloud application name and version find the corresponding openstack image
     * Since ucloud uses name and version and not a unique name/id
     * we use this config mapping instead of a strict naming convention of images
     * The config is also used to control which images are available
     *
     * This above will be faced out when the a naming convention is implemented.
     * New functionality
     * All ucloud app names and versions can be mapped to a name of a openstack image
     * using: ucloud-name-version
     * Example: ucloudName: ubuntu, ucloudVersion: 20.04 will give the openstack name: "ucloud-ubuntu-20.04"
     * We still need the id so get that from openstack
     */
    fun mapImage(name: String, version: String, toolImage: String?): String {
        // TODO Remove everything related to config when only correctly named images are used. aka use toolImage
        val image = provider.images.firstOrNull { it.ucloudName == name && it.ucloudVersion == version }
        var openstackImage: Image? = null

        logger.info("mapImage input: $version, $name, $toolImage .")

        if (image == null && toolImage != null) {
            logger.info("Trying to find image by tag $toolImage")
            openstackImage = listImages(mapOf("tag" to toolImage)).firstOrNull()
        }
        logger.info("Found image: $openstackImage")

        return when {
            image?.openstackId?.isNotBlank() == true -> {
                image.openstackId
            }

            image?.openstackName?.isNotBlank() == true -> {
                image.openstackName
            }

            openstackImage?.id?.isNotBlank() == true -> {
                openstackImage.id
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

        parameters["image"] = mapImage(
            job.specification.application.name,
            job.specification.application.version,
            job.status.resolvedApplication?.invocation?.tool?.tool?.description?.image
        )
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
                val flavor = getFlavorByName(parameters["flavor"]) ?: run {
                    val errorMessage = "Flavor not found: ${parameters["flavor"]}"
                    logger.error(errorMessage)
                    sendJobFailedMessage(job.id, errorMessage)
                    return@execute
                }

                // Set volume_size based on flavor
                parameters["volume_size"] = flavor.disk.toString()

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

                logger.error("Job: ${job.openstackName}: Exception when creating stack, message: $e.message")
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
     * Start creation monitor function for each job in a list of jobs
     *
     * The monitor function will send a status to UCloud when stack create is complete/failed
     */
    fun asyncMonitorCreations(jobs: List<Job>) {
        Thread.sleep(5000) // TODO Status is not set if we change this too quickly (https://github.com/SDU-eScience/UCloud/issues/2303)

        for (job in jobs) {
            threadPool.execute {
                monitorCreation(job)
                return@execute
            }
        }
    }

    /**
     * Monitor job and send status to ucloud when jobs are running or failed
     * Polls openstack continually for the corresponding stack
     * Based on the config, have a delay between each poll and a timeout to stop polling.
     * When the job is found in a create complete state, send the output IP to ucloud in a job running update
     * If this IP is not found or the stack isnt created within the timeout, send a job failed update
     */
    fun monitorCreation(job: Job) {
        val startTime = System.currentTimeMillis()
        while (startTime + config.monitor.timeout > System.currentTimeMillis()) {
            var stack: Stack? = null
            try {
                stack = getStackByJob(job)
            } catch (e: ConnectionException) {
                logger.error("Job: ${job.openstackName}: Connection timeout occurred: ${e.message}")
            } catch (e: ResponseException) {
                logger.error("Job: ${job.openstackName}: Read timeout occurred: ${e.message}")
            } catch (e: ServerResponseException) {
                logger.error("Job: ${job.openstackName}: Server responded with an error: ${e.message}")
            } catch (e: Exception) {
                logger.error("Job: ${job.openstackName}: An unexpected error occurred: ${e.message}")
            }
            logger.info("Job: ${job.openstackName}: Monitoring stack status $stack ${stack?.status}")
            if (stack != null && stack.status == StackStatus.CREATE_COMPLETE.name) {
                logger.info("Job: ${job.openstackName}: Stack CREATE complete")
                val outputIP = stack.outputs?.find { it["output_key"] == "server_ip" }
                if (!outputIP.isNullOrEmpty()) {
                    sendJobRunningMessage(job.id, outputIP["output_value"] as String)
                } else {
                    logger.error("Did not receive IP output from openstack $job $stack")
                    sendJobFailedMessage(job.id, "Did not receive IP output from openstack")
                }

                return
            }
            if (stack != null && stack.status == StackStatus.CREATE_FAILED.name) {
                logger.error("Job: ${job.openstackName}: Stack create failed status: ${stack.status} stackId: ${stack.id} stackName: ${stack.name} Reason: ${stack.stackStatusReason}")
                sendJobFailedMessage(job.id, "Job failed. Reason: ${stack.stackStatusReason}")
                return
            }
            // Sleep until next retry
            logger.info("Waiting to retry")
            Thread.sleep(config.monitor.delay)
        }
        // Job could not be started within the timeout
        logger.error("Job: ${job.openstackName}: Could not start job within timeout: $job.id")
        sendJobFailedMessage(job.id, "Job failed. The job could not be started at this time.")
    }

    /**
     * A shortcut function for sending job failed update message
     */
    fun sendJobFailedMessage(jobId: String, message: String) {
        // Try to identify lack of resources and change to user-friendly message
        val resourceErrorMsgs = listOf(
            "failed to get volume",
            "no valid host was found"
        )
        val jobMessage: String = if (resourceErrorMsgs.any { it in message.lowercase() }) {
            "Unfortunately all available resources have been allocated for the product type you have selected. " +
                    "The options available are to use an alternative machine size or product, or to try again later."
        } else {
            message
        }

        sendJobStatusMessage(
            jobId,
            JobState.FAILURE,
            MessageFormat.format(messages.jobs.createFailed, jobMessage)
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
     * A shortcut function for sending job instance shutdown message
     */
    fun sendInstanceShutdownMessage(jobId: String, deficit: Double?) {
        sendJobStatusMessage(
            jobId,
            JobState.SUSPENDED,
            MessageFormat.format(messages.jobs.instanceShutdown, deficit)
        )
    }

    /**
     * A shortcut function for sending job instance restart message
     */
    fun sendInstanceRestartedMessage(jobId: String) {
        sendJobStatusMessage(
            jobId,
            JobState.RUNNING,
            messages.jobs.instanceRestarted
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

    fun getActiveStacks(): List<Stack> {
        val client = getClient()

        return client.heat().stacks().list().filter {
            it.status in listOf(
                StackStatus.CREATE_COMPLETE.name,
                StackStatus.UPDATE_COMPLETE.name,
                StackStatus.UPDATE_FAILED.name,
                StackStatus.RESUME_COMPLETE.name,
                StackStatus.CHECK_COMPLETE.name
            )
        }
    }

    /**
     * For each stack in openstack, charge the corresponding job in ucloud.
     */
    fun chargeAllStacks() {
        getActiveStacks().forEach {
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

    fun retrieveUcloudJob(ucloudId: String): Job? {
        val response: IngoingCallResponse<Job, CommonErrorMessage> = JobsControl.retrieve.call(
            ResourceRetrieveRequest(
                JobIncludeFlags(includeProduct = true),
                ucloudId
            ),
            uCloudClient
        )

        if (response is IngoingCallResponse.Ok) {
            return response.result
        } else {
            // Handle backwards compatibility
            // Ucloud changed their ids for jobs but the
            logger.info("Couldn't find ucloud job by id. Trying old providerId. UcloudId: $ucloudId")
            val jobs: PageV2<Job> = JobsControl.browse.call(
                ResourceBrowseRequest(
                    JobIncludeFlags(
                        filterProviderIds = ucloudId,
                        includeProduct = true
                    )
                ),
                uCloudClient
            ).orThrow()
            if (jobs.items.isNotEmpty()) {
                logger.info("Found job in UCloud from old providerId. stackUCloudId: $ucloudId: UCloudid: ${jobs.items.first().id}")
                return jobs.items.first()
            } else {
                logger.info("Couldn't find ucloud job from old providerId. UcloudId: $ucloudId")
            }
        }
        return null
    }

    fun checkCreditsJob(
        jobId: String,
        lastChargedTime: String,
        cpuCores: Long,
        period: Long
    ): ResourceChargeCreditsResponse {
        return JobsControl.checkCredits.call(
            BulkRequest(
                listOf(
                    ResourceChargeCredits(
                        jobId,
                        lastChargedTime,
                        cpuCores,
                        period
                    )
                )
            ),
            uCloudClient
        ).orThrow()
    }

    fun chargeCreditsJob(
        jobId: String,
        lastChargedTime: String,
        cpuCores: Long,
        period: Long
    ): ResourceChargeCreditsResponse {
        // Units is the number of cores
        // Period is the time spent.
        //   - This is counted in minutes/hours or days depending on the products unitOfPrice
        return JobsControl.chargeCredits.call(
            BulkRequest(
                listOf(
                    ResourceChargeCredits(
                        jobId,
                        lastChargedTime,
                        cpuCores,
                        period
                    )
                )
            ),
            uCloudClient
        ).orThrow()
    }

    fun calculatePeriodAndNewChargeTimeForProduct(product: Product, stack: Stack): Pair<Long, Instant> {
        val chargeTime: Instant = Instant.now()
        val lastChargedTime: Instant = getLastChargedFromStack(stack)
        val duration = Duration.between(lastChargedTime, chargeTime)

        val period: Long
        val newChargeTime: Instant
        when (product.unitOfPrice) {
            in arrayOf(
                ProductPriceUnit.CREDITS_PER_HOUR,
                ProductPriceUnit.UNITS_PER_HOUR
            ) -> {
                period = duration.toHours()
                newChargeTime = lastChargedTime.plus(period, ChronoUnit.HOURS)
            }

            in arrayOf(
                ProductPriceUnit.CREDITS_PER_MINUTE,
                ProductPriceUnit.UNITS_PER_MINUTE
            ) -> {
                period = duration.toMinutes()
                newChargeTime = lastChargedTime.plus(period, ChronoUnit.MINUTES)
            }

            else -> {
                logger.error("ProductUnitOfPrice is not hours or minutes: ${product.unitOfPrice}")
                throw Exception("ProductUnitOfPrice is not hours or minutes: ${product.unitOfPrice}")
            }
        }
        return Pair(period, newChargeTime)
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
        // Get corresponding ucloud job
        val job: Job? = retrieveUcloudJob(stack.ucloudId)

        // No charge if ucloud job not found
        if (job == null) {
            logger.error("Could not charge job. Could not find job in UCloud: ${stack.ucloudId}")
            return
        }

        // No charge If stack instance is shelved
        val instance = getInstanceFromStack(stack)
        if (instance == null) {
            logger.error("Instance not found: ${stack.name}")
            return
        }

        val product: Product.Compute? = job.status.resolvedProduct
        // No charge if product not found
        if (product == null) {
            logger.error("Could not charge job. Job had not resolved product. UcloudId: ${stack.ucloudId}")
            return
        }

        val cpuCores: Int? = product.cpu
        // No charge if cpucores not found
        if (cpuCores == null) {
            logger.error("Could not charge job. Product had no cpu count. UcloudId: ${stack.ucloudId}")
            return
        }

        val (period, newChargeTime) = calculatePeriodAndNewChargeTimeForProduct(product, stack)

        if (instance.status == Server.Status.SHELVED_OFFLOADED) {
            logger.info("Instance is SHELVED. Stack ${stack.name}. ucloud job was not charged for $cpuCores cores for period $period")
            updateStackLastCharged(stack, newChargeTime)
            return
        }

        // No charge if period is zero or below
        if (period <= 0L) {
            logger.error("Period: $period Dont charge zero or negative hours/minutes. UcloudId: ${stack.ucloudId}")
            return
        }

        // verify job has enough credits to charge. Stop job if insufficient funds
        logger.info("Will now check credits: ${job.id} ${product.unitOfPrice} ${getLastChargedFromStack(stack)} ${cpuCores.toLong()} $period")
        val checkResponse =
            checkCreditsJob(job.id, getLastChargedFromStack(stack).toString(), cpuCores.toLong(), period)
        val insufficientFunds =
            checkResponse.insufficientFunds.isNotEmpty() && checkResponse.insufficientFunds.contains(FindByStringId(job.id))

        if (insufficientFunds) {
            if (instance.status != Server.Status.SHUTOFF) {
                // Shut down stack instance and monitor status then send update to ucloud
                logger.info("Stack ${stack.name}. Server status: ${instance.status}. ucloud job has insufficient funds and will be stopped. $checkResponse")
                sendInstanceShutdownAction(instance)
                asyncMonitorShutdownInstanceSendUpdate(instance, job, period)
            } else {
                logger.info("Instance is SHUTOFF and out of funds. Stack ${stack.name}. ucloud job was not charged for $cpuCores cores for period $period")
            }
            return
        }

        // Charging can be done, start machine if not running

        if (instance.status == Server.Status.SHUTOFF) {
            // Server is shutoff but charging can be done again. Restart the server and continue charging
            logger.info("Stack ${stack.name}. Server status: ${instance.status}. ucloud job can charge, starting instance . $checkResponse")
            sendInstanceStartAction(instance)
            asyncMonitorStartInstanceSendUpdate(instance, job)
        }

        // When we arrive here, the instance is active (or starting) and the job can be charged in ucloud.
        logger.info("Will now charge: ${job.id} ${product.unitOfPrice} ${getLastChargedFromStack(stack)} ${cpuCores.toLong()} $period")
        chargeJob(job, stack, instance, period, cpuCores, newChargeTime)

    }

    fun chargeJob(job: Job, stack: Stack, instance: Server, period: Long, cpuCores: Int, newChargeTime: Instant) {
        val chargeResponse =
            chargeCreditsJob(job.id, getLastChargedFromStack(stack).toString(), cpuCores.toLong(), period)

        if (chargeResponse.insufficientFunds.isNotEmpty()) {
            logger.info("Stack ${stack.name}. ucloud job has insufficient funds and will be stopped. $chargeResponse")
            // Shut down stack instance and monitor status then send update to ucloud.
            // I know this was verified above in the checkCredits
            sendInstanceShutdownAction(instance)
            asyncMonitorShutdownInstanceSendUpdate(instance, job, period)
        } else if (chargeResponse.duplicateCharges.isNotEmpty()) {
            // Duplicate charges
            logger.info("Stack ${stack.name}. ucloud job duplicate charges. $chargeResponse")
        } else {
            // Everything is good
            logger.info("Stack ${stack.name}. ucloud job was charged for $cpuCores cores for period $period. $chargeResponse")

            updateStackLastCharged(stack, newChargeTime)
        }
        logger.info("Charge response: $chargeResponse")
    }

    /**
     * Retrieve lastCharged timestamp from stack in openstack
     *
     * The timestamp is saved as a text tag on the stack and includes a prefix
     * Get this timestamp, remove prefix and parse to Instant
     * If timestamp not found, return creation timestamp from stack
     */
    fun getLastChargedFromStack(stack: Stack): Instant {
        val prefix = "lastcharged:"
        val lastCharged = stack.tags?.firstOrNull { it.contains(prefix) }?.removePrefix(prefix)

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
    fun updateStackLastCharged(listStack: Stack, chargedAt: Instant): Boolean {
        // FIXME Store in database not openstack tags
        // Alternatively: Store on metadata on instance

        //Get the stack with all details.
        //When stack is retrieved from list it doesn't have parameters
        val client = getClient()
        val stack = client.heat().stacks().getStackByName(listStack.name)
        if (stack == null) {
            logger.error("Could not update lastcharged. Stack not found: $listStack.name")
            return false
        }

        logger.info("[Charge] Timestamped from ${getLastChargedFromStack(stack)} to $chargedAt")

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
            logger.info("Stack lastcharged timestamp updated: $stack}")
        } else {
            logger.error("Stack lastcharged timestamp could no be updated: $stack}")
        }

        return update.isSuccess
    }

    fun sendInstanceShutdownAction(server: Server) {
        val client = getClient()
        logger.info("Sending STOP signal to server ${server.id}")
        client.compute().servers().action(server.id, Action.STOP)
    }

    fun asyncMonitorShutdownInstanceSendUpdate(server: Server, job: Job, period: Long) {
        scope.launch {
            try {
                monitorShutdownInstanceSendUpdate(server, job, period)
            } catch (e: Exception) {
                logger.error("Job: ${job.openstackName} Exception in asyncMonitorShutdownInstanceSendUpdate coroutine", e)
            }
        }
    }

    fun getInstanceFromId(id: String): Server? {
        val client = getClient()
        return client.compute().servers().get(id)
    }

    fun monitorShutdownInstanceSendUpdate(server: Server, job: Job, period: Long) {
        val startTime = System.currentTimeMillis()

        while (startTime + config.monitor.timeout > System.currentTimeMillis()) {
            val instance = getInstanceFromId(server.id)
            if (instance != null && instance.status == Server.Status.SHUTOFF) {
                logger.info("Job: ${job.openstackName} Found instance with status SHUTOFF: ${instance.id}")
                sendInstanceShutdownMessage(job.id, estimateProductPriceForPeriod(job, period))
                return
            } else if (instance == null) {
                logger.info("Instance stopping could not find instance: ${server.id}")
            } else {
                logger.info("Instance stopping could not be verified. Status: ${server.status}")
            }
            // Sleep until next retry
            logger.info("Waiting to retry")
            Thread.sleep(config.monitor.delay)
        }
        logger.error("Job: ${job.openstackName} Instance could no be stopped. Serverid: ${server.id}")
    }

    fun sendInstanceStartAction(server: Server) {
        val client = getClient()
        logger.info("Sending START signal to server ${server.id}")
        client.compute().servers().action(server.id, Action.START)
    }

    fun asyncMonitorStartInstanceSendUpdate(server: Server, job: Job) {
        scope.launch {
            try {
                monitorStartInstanceSendUpdate(server, job)
            } catch (e: Exception) {
                logger.error("Job: ${job.openstackName} in asyncMonitorStartInstanceSendUpdate coroutine", e)
            }
        }
    }

    fun monitorStartInstanceSendUpdate(server: Server, job: Job) {
        val startTime = System.currentTimeMillis()

        while (startTime + config.monitor.timeout > System.currentTimeMillis()) {
            val instance = getInstanceFromId(server.id)
            if (instance != null && instance.status == Server.Status.ACTIVE) {
                logger.info("Found instance with status ACTIVE: ${instance.id}")
                sendInstanceRestartedMessage(job.id)
                return
            } else if (instance == null) {
                logger.info("Instance starting could not find instance: ${server.id}")
            } else {
                logger.info("Instance starting could not be verified. Status: ${server.status}")
            }
            // Sleep until next retry
            logger.info("Waiting to retry")
            Thread.sleep(config.monitor.delay)
        }
        logger.error("Instance could no be started within timeout. Serverid: ${server.id}")
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

    /**
     * Charge a job before deleting it.
     * FIXME Maybe add logic to disallow deletion if charging is unsuccessful.
     */
    fun asyncChargeDeleteJob(job: Job) {
        threadPool.execute {
            val stack = getStackByJob(job)
            if (stack != null) {
                logger.info("AsyncChargeDeleteJob attempting to charge and delete job: ${job.id} Stack: ${job.openstackName}")
                chargeStack(stack)
                deleteJob(job)
            }
            return@execute
        }
    }

    /**
     * Takes a job and finds the corresponding stack then sends a stack delete request to openstack.
     *
     * First retrieve the stack based on the id on job
     * then send delete request with id and name from job
     */
    fun deleteJob(job: Job) {
        // TODO: clean up this method after test
        logger.info("Job: ${job.openstackName} In delete method.")
        val client = getClient()
        var stackByName: Stack? = getStackByJob(job)
        try {
            stackByName = getStackByJob(job)
        } catch (e: Exception) {
            logger.info("Job: ${job.openstackName} Exception when getting stack, message: ${e.message}")
        }
        if (stackByName != null) {
            logger.info("Job: ${job.openstackName} Deleting stack: ${stackByName.name}")
            val delete = client.heat().stacks().delete(stackByName.name, stackByName.id)
            if (!delete.isSuccess) {
                logger.error("Job: ${job.openstackName} Stack deletion request was unsuccessful ${stackByName.name}")
            } else {
                logger.info("Job: ${job.openstackName} Stack deletion request was successful ${stackByName.name}")
            }
        } else {
            logger.info("Job: ${job.openstackName} Stack not found with name: ${job.openstackName}")
        }
    }

    /**
     * Start deletion monitor function for each job in a list of jobs
     *
     * The monitor function will send a status to UCloud when stack is found in deleted status
     */
    fun asyncMonitorDeletions(jobs: List<Job>) {
        Thread.sleep(5000) // TODO Status is not set if we change this too quickly (https://github.com/SDU-eScience/UCloud/issues/2303)

        for (job in jobs) {
            threadPool.execute {
                monitorDeletion(job)
                return@execute
            }
        }
    }

    /**
     * Monitor job and send status to ucloud when jobs are found in deleted state
     * Polls openstack continually for the corresponding stack
     * Based on the config, have a delay between each poll and a timeout to stop polling.
     * When the job is found in a delete complete state, send the output IP to ucloud in a job success update
     * If this IP is not found or the stack isn't created within the timeout, don't send status
     */
    fun monitorDeletion(job: Job) {
        val startTime = System.currentTimeMillis()
        while (startTime + config.monitor.timeout > System.currentTimeMillis()) {
            val stack = findStackIncludeDeleted(job)
            if (stack != null && stack.status == StackStatus.DELETE_COMPLETE.name) {
                logger.info("Job: ${job.openstackName} Found stack with status delete complete: ${job.openstackName}")
                sendJobStatusMessage(job.id, JobState.SUCCESS, "Stack DELETE complete")
                return
            } else if (stack == null) {
                logger.info("Stack deletion could not find stack: ${job.openstackName}")
            } else {
                logger.info("Stack deletion could not be verified: ${job.openstackName} Status: ${stack.status}")
            }
            // Sleep until next retry
            logger.info("Waiting to retry")
            Thread.sleep(config.monitor.delay)
        }
        logger.error("Job: ${job.openstackName} Job could not be deleted: $job")
    }

    fun asyncMonitorStackSuspensions(jobs: List<Job>) {
        Thread.sleep(5000) // TODO Status is not set if we change this too quickly (https://github.com/SDU-eScience/UCloud/issues/2303)

        for (job in jobs) {
            threadPool.execute {
                monitorInstance(job, InstanceStatus.SHELVED_OFFLOADED)
                return@execute
            }
        }
    }

    fun asyncMonitorStackResumes(jobs: List<Job>) {
        Thread.sleep(5000) // TODO Status is not set if we change this too quickly (https://github.com/SDU-eScience/UCloud/issues/2303)

        for (job in jobs) {
            threadPool.execute {
                monitorInstance(job, InstanceStatus.ACTIVE)
                return@execute
            }
        }
    }

    fun monitorInstance(job: Job, expectedStatus: InstanceStatus) {
        val startTime = System.currentTimeMillis()
        while (startTime + config.monitor.timeout > System.currentTimeMillis()) {
            val stack = getStackByJob(job)

            if (stack != null) {
                val instance = getInstanceFromStack(stack)
                if (instance != null && instance.status.name == expectedStatus.name) {
                    logger.info("Found stack with status delete complete: ${job.openstackName}")
                    // TODO Implement when JobState.SUSPENDED is available
                    // Status should be based on expected instance state
                    // InstanceState.ACTIVE = JobState.RUNNING
                    // InstanceState.SHELVED_OFFLOAED = JobState.SUSPENDED
                    // sendJobStatusMessage(job.id, JobState.SUCCESS, "Suspend complete")
                    return
                } else {
                    logger.info("Monitor stack suspend could not find instance from stack: ${stack.id}")
                }
            } else {
                logger.info("Monitor stack suspend could not find stack from Job: ${job.openstackName}")
            }

            // Sleep until next retry
            logger.info("Waiting to retry")
            Thread.sleep(config.monitor.delay)
        }
        logger.error("Instance was not found in expected state: $expectedStatus. JobId: ${job.id}")
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
     * If the stack status is different from expected send status update to ucloud.
     */
    fun verifyJob(job: Job) {
        val stack = getStackByJob(job, includeDeleted = true)

        if (stack == null) {
            logger.info("Job: ${job.openstackName} Could not find stack with jobid ${job.id}. Assume nothing.")
            return
        }

        val instance = getInstanceFromStack(stack)
        var expectedUcloudJobState: JobState? = null

        when {
            StackStatus.valueOf(stack.status) in listOf(StackStatus.CREATE_COMPLETE, StackStatus.UPDATE_COMPLETE) -> {
                expectedUcloudJobState = if (instance?.status == Server.Status.SHUTOFF) {
                    JobState.SUSPENDED
                } else {
                    JobState.RUNNING
                }
            }

            StackStatus.valueOf(stack.status) == StackStatus.CREATE_IN_PROGRESS -> {
                expectedUcloudJobState = JobState.IN_QUEUE
            }

            StackStatus.valueOf(stack.status) in listOf(StackStatus.CREATE_FAILED, StackStatus.DELETE_COMPLETE) -> {
                expectedUcloudJobState = JobState.FAILURE
            }
        }

        when {
            expectedUcloudJobState == null -> {
                if (StackStatus.valueOf(stack.status) == StackStatus.DELETE_IN_PROGRESS) {
                    logger.info("Job: ${job.openstackName} Delete in progress, waiting to verify status: ${stack.status}. JobID: ${job.id}. Stack: ${stack.id}")
                } else {
                    // Unknown stack status.
                    logger.error("Job: ${job.openstackName} Unhandled status: ${stack.status}. JobID: ${job.id}. Stack: ${stack.id}")
                }
            }

            job.status.state != expectedUcloudJobState -> {
                // Status in ucloud is not as expected. Send update.
                logger.info("Job: ${job.openstackName} Updating status: Stack ${stack.id} ${stack.status}. Job ${job.id} ${job.status.state}")
                sendJobStatusMessage(job.id, expectedUcloudJobState, "Stack status: ${stack.status}")
            }
            else -> {
                logger.info("Job: ${job.openstackName} Status verified: Stack ${stack.id} ${stack.status}. Job ${job.id} ${job.status.state}")
            }
        }
    }

    fun estimateProductPriceForPeriod(job: Job, period: Long): Double? {
        val product: Product.Compute? = job.status.resolvedProduct
        if (product == null) {
            logger.error("Cannot estimate price for job, no resolvedProduct found. jobId: ${job.id}")
        }

        val priceForAllCpus = product?.cpu?.toLong()?.times(product.pricePerUnit)
        return priceForAllCpus?.times(period)?.div(1000000.0)
    }

    fun getInstanceFromStack(stack: Stack): Server? {
        val client = getClient()

        val resources = client.heat().resources().list(stack.id)
        val serverResource = resources.firstOrNull { x -> x.resourceName.equals("server") } ?: return null

        return client.compute().servers().get(serverResource.physicalResourceId)
    }

    fun suspendJobs(jobs: List<Job>) {
        for (job in jobs) {
            val stack = getStackByJob(job)
            if (stack != null) {
                chargeStack(stack)
                suspendStack(stack)
            } else {
                logger.error("SuspendJobs could not find stack. JobId: ${job.id} StackName: ${job.openstackName}")
            }
        }
    }

    fun suspendStack(stack: Stack) {
        val client = getClient()

        val resources = client.heat().resources().list(stack.id)
        val serverResource = resources.first { x -> x.resourceName.equals("server") }
        client.compute().servers().action(serverResource.physicalResourceId, Action.SHELVE)
        logger.info("Action.SHELVE sent to instance. StackId: ${stack.id}")
    }

    fun resumeJobs(jobs: List<Job>) {
        for (job in jobs) {
            val stack = getStackByJob(job)
            if (stack != null) {
                chargeStack(stack)
                resumeStack(stack)
            } else {
                logger.error("ResumeJobs could not find stack. JobId: ${job.id} StackName: ${job.openstackName}")
            }
        }
    }

    fun resumeStack(stack: Stack) {
        val client = getClient()

        val resources = client.heat().resources().list(stack.id)
        val serverResource = resources.first { x -> x.resourceName.equals("server") }
        client.compute().servers().action(serverResource.physicalResourceId, Action.UNSHELVE)
        logger.info("Action.UNSHELVE sent to instance. StackId: ${stack.id}")
    }

    fun removeFailedJobs() {
        val client = getClient()

        val failedStacks = client.heat().stacks().list().filter {
            it.status in listOf(
                StackStatus.CREATE_FAILED.name,
                StackStatus.DELETE_FAILED.name
            )
        }

        logger.info("Found ${failedStacks.size} that will be deleted")
        for (failedStack in failedStacks) {
            val job: Job? = retrieveUcloudJob(failedStack.ucloudId)

            // Delete stack and send update if job found
            logger.info("RemoveFailedJobs Deleting stack: ${failedStack.id} - ${failedStack.stackStatusReason}")
            client.heat().stacks().delete(failedStack.name, failedStack.id)
            if (job != null) {
                asyncMonitorDeletions(listOf(job))
            }
        }
    }

    fun deleteNotChargedStacks() {
        // Delete all stacks that have not been charged for a period and their instance is shut off

        for (activeStack in getActiveStacks()) {
            val lastCharged = getLastChargedFromStack(activeStack)
            val sinceLastCharge = Duration.between(lastCharged, Instant.now())
            val instance = getInstanceFromStack(activeStack)
            val isShutoff = instance?.status == Server.Status.SHUTOFF

            if (isShutoff) {
                //When stack is retrieved from list it doesn't have parameters
                val stackWithParameters = getStackByName(activeStack.name)
                if (stackWithParameters == null) {
                    logger.error("Could fetch stack details in deleteNotChargedStacks. Stack not found: $activeStack.name")
                    continue
                }

                // See if any specific max lifetime exists for this flavor
                val flavorLifetime = config.janitor.flavorLifetimes.find {
                    it.flavorName == stackWithParameters.parameters["flavor"]
                }
                val maxLifetime = flavorLifetime?.lifetimeDays ?: config.janitor.deleteShutoffInstanceAfterDays
                logger.info("Stack with shutoff instance found ${activeStack.id} $lastCharged, $isShutoff, time since last charge in hours: ${sinceLastCharge.toHours()}")
                if (sinceLastCharge.toDays() >= maxLifetime) {
                    val job: Job? = retrieveUcloudJob(activeStack.ucloudId)

                    if (job == null) {
                        logger.error("Could not delete not charged job. Could not find job in UCloud: ${activeStack.ucloudId}")
                        return
                    }

                    logger.info("Deleting stack with shutoff instance ${activeStack.id} $lastCharged, $isShutoff, time since last charge in days: ${sinceLastCharge.toDays()}")
                    deleteJob(job)
                    asyncMonitorDeletions(listOf(job))
                }

            }
        }
    }

    fun sendCreditDeficitUpdates() {
        for (activeStack in getActiveStacks()) {
            val lastCharged = getLastChargedFromStack(activeStack)
            val sinceLastCharge = Duration.between(lastCharged, Instant.now())
            val instance = getInstanceFromStack(activeStack)
            val isShutoff = instance?.status == Server.Status.SHUTOFF

            if (isShutoff) {
                val job: Job? = retrieveUcloudJob(activeStack.ucloudId)

                if (job == null) {
                    logger.error("Could not send deficit update. Could not find job in UCloud: ${activeStack.ucloudId}")
                    return
                }

                logger.info("Sending deficit update with shutoff instance ${activeStack.id} $lastCharged, $isShutoff ${sinceLastCharge.toDays()}")

                val msg = MessageFormat.format(
                    messages.jobs.creditDeficit,
                    estimateProductPriceForPeriod(job, sinceLastCharge.toMinutes())
                )

                sendJobStatusMessage(job.id, JobState.SUSPENDED, msg)
            }
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