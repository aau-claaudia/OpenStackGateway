package dk.aau.claaudia.openstackgateway.services

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import dk.aau.claaudia.openstackgateway.config.Messages
import dk.aau.claaudia.openstackgateway.config.OpenStackProperties
import dk.aau.claaudia.openstackgateway.config.ProviderProperties
import dk.aau.claaudia.openstackgateway.extensions.getLogger
import dk.aau.claaudia.openstackgateway.extensions.openstackName
import dk.aau.claaudia.openstackgateway.extensions.ucloudId
import dk.aau.claaudia.openstackgateway.models.StackStatus
import dk.sdu.cloud.app.orchestrator.api.*
import dk.sdu.cloud.app.store.api.AppParameterValue
import dk.sdu.cloud.app.store.api.SimpleDuration
import dk.sdu.cloud.calls.client.orThrow
import dk.sdu.cloud.providers.UCloudClient
import dk.sdu.cloud.providers.call
import io.ktor.http.*
import org.openstack4j.api.Builders
import org.openstack4j.api.OSClient.OSClientV3
import org.openstack4j.model.common.Identifier
import org.openstack4j.model.compute.Flavor
import org.openstack4j.model.compute.Server
import org.openstack4j.model.heat.Event
import org.openstack4j.model.heat.Stack
import org.openstack4j.model.heat.Template
import org.openstack4j.model.identity.v3.Token
import org.openstack4j.model.identity.v3.User
import org.openstack4j.model.image.v2.Image
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

    private fun getClient(): OSClientV3 {
        //OSFactory.enableHttpLoggingFilter(true)
        if (token?.hasExpired() == false) {
            logger.info("Using existing token")
            return OSFactory.clientFromToken(token)
        } else {
            logger.info("Getting new token")
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
            logger.info("Saving token")
            token = client.token
            return client
        }
    }

    private fun prefixStackName(ucloudJobId: String): String {
        return "${config.stackPrefix}${ucloudJobId}"
    }

    fun test(): String {
        val os = getClient()

        try {
            val users: List<User?> = os.identity().users().list()
            logger.info(users.toString())

        } catch (e: Exception) {
            logger.error("Error getting users, ", e)
        }
        val flavors: List<Flavor?> = os.compute().flavors().list()
        logger.info(flavors.toString())

        val servers: List<Server?> = os.compute().servers().list()
        logger.info(servers.toString())

        os.heat().templates()

        return os.token.toString()
    }

    fun listFlavors(): List<Flavor?> {
        val list = getClient().compute().flavors().list()
        logger.info("found flavors:", list)
        return list
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
        val img = getClient().imagesV2().get(id)
        logger.info("image: $img")
        return img
    }

    fun getFlavorById(id: String?): Flavor? {
        val flavor = getClient().compute().flavors().get(id)
        logger.info("flavor by id: $flavor")
        return flavor
    }

    fun getFlavorByName(name: String?): Flavor? {
        val flavor = getClient().compute().flavors().list().first{ it.name == name}
        logger.info("flavor by name: $flavor")
        return flavor
    }

    fun listStacks(): List<Stack> {
        return getClient().heat().stacks().list()
    }

    fun getStackOld(name: String): Stack? {
        return getClient().heat().stacks().getStackByName(prefixStackName(name))
    }

    fun getStackByName(name: String): Stack? {
        return getClient().heat().stacks().getStackByName(name)
    }

    fun mapImage(name: String, version: String): String {
        val image = provider.images.firstOrNull{ it.ucloudName == name && it.ucloudVersion == version }

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
        parameters["az"] = config.availabilityZone

        return parameters
    }

    fun createStacks(jobs: List<Job>){
        logger.info("creating stacks: $jobs.size")

        // There is only one template for now
        val template = templateService.getTemplate("ucloud")

        for (job in jobs) {
            // Starts a thread for each job
            asyncStackCreation(job, template)
        }
    }

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
                    throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing parameters: $missingParameters")
                }

                // Verify flavor exists
                getFlavorByName(parameters["flavor"])
                    ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Flavor not found: ${parameters["flavor"]}")

                // Verify image exists
                getImage(parameters["image"])
                    ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Image not found: ${parameters["image"]}")

                // TODO Verify security group exists

                // Send stack creation to openstack
                createStack(job.id, template.templateJson, parameters)
            } catch (e: Exception) {
                // Send message to ucloud
                sendJobStatusMessage(job.id, JobState.FAILURE,
                    MessageFormat.format(messages.jobs.createFailed)
                )
                // Raise exception for logging
                throw e
            }

            return@execute
        }
    }

    fun createStack(name: String, template: String, parameters: MutableMap<String, String>): Stack? {
        logger.info("Create stack: $name", template, parameters)

        val client = getClient()

        val build = Builders.stack()
            .template(template)
            .parameters(parameters)
            .name(prefixStackName(name))
            .timeoutMins(60)
            .build()

        // TODO Consider passing parameters as a file
        //Builders.stack().files(mutableMapOf("file1" to "file contents"))

        val created = client.heat().stacks().create(build)
        //The stack returned by create only has id and href
        //But I guess this only returns a 200
        return getStackByName(created.name)
    }

    fun sendStatusWhenStackComplete(jobs: List<Job>) {
        Thread.sleep(5000) // TODO Status is not set if we change this too quickly (https://github.com/SDU-eScience/UCloud/issues/2303)

        for (job in jobs) {
            threadPool.execute {
                val startTime = System.currentTimeMillis()
                while (startTime + config.monitor.timeout > System.currentTimeMillis()) {
                    val stack = getStackByName(job.openstackName)
                    logger.info("Monitoring stack status", stack, stack?.status)
                    if (stack != null && stack.status == StackStatus.CREATE_COMPLETE.name) {
                        logger.info("Stack CREATE complete")
                        val outputIP = stack.outputs.find { it["output_key"] == "server_ip" }
                        if (!outputIP.isNullOrEmpty()) {
                            sendJobStatusMessage(job.id, JobState.RUNNING,
                                MessageFormat.format(messages.jobs.createComplete, outputIP["output_value"]))
                        } else {
                            logger.error("Did not receive IP output from openstack", job, stack)
                            sendJobStatusMessage(job.id, JobState.FAILURE,
                                MessageFormat.format(messages.jobs.createFailed))
                        }

                        return@execute
                    }
                    // Sleep until next retry
                    logger.info("Waiting to retry")
                    Thread.sleep(config.monitor.delay)
                }
            }
        }
    }

    fun sendJobStatusMessage(jobId: String, state: JobState, message: String) {
        JobsControl.update.call(
            JobsControlUpdateRequest(
                listOf(
                    JobsControlUpdateRequestItem(
                        jobId,
                        state,
                        message
                    )
                )
            ),
            uCloudClient
        ).orThrow()
    }

    //TEST REMOVE ME:
    fun getAllStacks(){
        val client = getClient()
        val list = client.heat().stacks().list().filter { it.status in listOf("CREATE_COMPLETE", "UPDATE_COMPLETE") }
        list.forEach{
            logger.info(it.tags.joinToString())
        }
    }

    fun chargeAllJobs() {
        val client = getClient()

        val list = client.heat().stacks().list().filter { it.status in listOf("CREATE_COMPLETE", "UPDATE_COMPLETE") }
        logger.info("list: ${list.size}")

        list.forEach{
            asyncChargeJob(it)
        }
    }

    fun asyncChargeJob(stack: Stack) {
        // TODO Make this asynchronous
        val job = JobsControl.retrieve.call(JobsControlRetrieveRequest(stack.ucloudId), uCloudClient)
        logger.info("Found job $job")

        if (job.statusCode == HttpStatusCode.NotFound) {
            logger.info("Job not found in ucloud. UcloudId: ${stack.ucloudId}")
            return
        }

        //Send a charge request to ucloud. Duration is time since last charge

        val chargeTime: Instant = Instant.now()
        val lastChargedTime: Instant  = getLastChargedFromStack(stack)

        val duration = Duration.between(lastChargedTime, chargeTime)
        val simpleDuration = SimpleDuration(duration.toHours().toInt(),duration.toMinutesPart(),duration.toSecondsPart())

        val response = JobsControl.chargeCredits.call(
            JobsControlChargeCreditsRequest(
                listOf(
                    JobsControlChargeCreditsRequestItem(
                        // Er chargedate okay som chargeId
                        stack.ucloudId, lastChargedTime.toString(), simpleDuration
                    )
                )
            ),
            uCloudClient
        ).orThrow()

        if (response.duplicateCharges.isEmpty() && response.duplicateCharges.isEmpty()) {
            // Everything is good
            logger.info("Stack ${stack}. ucloud job was charged for $simpleDuration. $response")
            updateStackLastCharged(stack, chargeTime)
        } else if (response.insufficientFunds.isNotEmpty()){
            // Insufficient funds
            logger.info("Stack ${stack}. ucloud job has insufficient funds. $response")
        } else if (response.duplicateCharges.isNotEmpty()){
            // Duplicate charges
            logger.info("Stack ${stack}. ucloud job duplicate charges. $response")
            updateStackLastCharged(stack, chargeTime)
        }
        logger.info("Charge response:", response)
    }

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

    fun updateStackLastCharged(listStack: Stack, chargedAt: Instant) {
        // FIXME Store in database not openstack tags
        // Alternatively: Store on metadata on instance

        //Start by getting the stack with all details.
        //When stack is retreived from list it doesnt have parameters?

        val stack = getStackByName(listStack.name)
        if (stack == null) {
            logger.error("Could not update lastcharged. Stack not found: $listStack.name")
            return
        }

        val client = getClient()
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

    fun deleteJobs(jobs: List<Job>) {
        for (job in jobs) {
            // FIXME DETTE SKAL OGSÅ VÆRE EN TRÅD PER JOB
            deleteJob(job)
        }
    }

    fun deleteJob(job: Job) {
        val client = getClient()

        val stackByName = client.heat().stacks().getStackByName(job.openstackName)
        if (stackByName != null) {
            logger.info("Deleting stack: ${stackByName.name}")
            val delete = client.heat().stacks().delete(stackByName.name, stackByName.id)
            if (!delete.isSuccess) {
                throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Stack could not be deleted")
            }
        } else {
            logger.info("Stack not found with name: ${job.openstackName}")
            // Maybe dont throw exception here?
            //throw ResponseStatusException(HttpStatus.NOT_FOUND, "Stack not found")
        }
    }

    fun monitorStackDeletions(jobs: List<Job>) {
        Thread.sleep(5000) // TODO Status is not set if we change this too quickly (https://github.com/SDU-eScience/UCloud/issues/2303)

        for (job in jobs) {
            threadPool.execute {
                val startTime = System.currentTimeMillis()
                while (startTime + config.monitor.timeout > System.currentTimeMillis()) {
                    val stack = getStackByName(job.openstackName)
                    if (stack == null) {
                        logger.info("Could not find stack. Assume deleted", stack)
                        sendJobStatusMessage(job.id, JobState.SUCCESS, "Stack DELETE complete")
                        return@execute

                    }
                    logger.info("Stack was found and not yet deleted", stack)
                    // Sleep until next retry
                    logger.info("Waiting to retry")
                    Thread.sleep(config.monitor.delay)
                }
            }
        }
    }

    fun verifyJobs(jobs: List<Job>) {
        for (job in jobs) {
            verifyJob(job)
        }
    }

    fun verifyJob(job: Job) {
        val stack = getStackByName(job.openstackName)

        if (stack == null) {
            logger.info("Could not find stack. Assume deleted", job, stack)
            sendJobStatusMessage(job.id, JobState.SUCCESS, "Stack was deleted")
            return
        }

        // Move this to config?
        val statusMappings = mapOf<StackStatus, JobState>(
            StackStatus.CREATE_COMPLETE to JobState.RUNNING,
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
                    "ERROR, Unknown status: ${job.currentState.name}")
            }
            job.currentState != expectedJobState -> {
                // Status in ucloud is not as expected. Send update.
                logger.info("Job status not as expected, updating ucloud: ${stack.status}", job, stack)
                sendJobStatusMessage(job.id, expectedJobState, "Stack status: ${stack.status}")
            }
            else -> {
                logger.info("Status verified: ${stack.status}", job, stack)
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

    companion object {
        val logger = getLogger()
    }
}