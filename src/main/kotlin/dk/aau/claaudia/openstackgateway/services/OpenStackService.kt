package dk.aau.claaudia.openstackgateway.services

import dk.aau.claaudia.openstackgateway.config.OpenStackProperties
import dk.aau.claaudia.openstackgateway.extensions.getLogger
import dk.aau.claaudia.openstackgateway.models.StackStatus
import dk.sdu.cloud.app.orchestrator.api.*
import dk.sdu.cloud.app.store.api.AppParameterValue


import dk.sdu.cloud.calls.client.orThrow
import dk.sdu.cloud.providers.UCloudClient
import dk.sdu.cloud.providers.call
import org.openstack4j.api.Builders
import org.openstack4j.api.OSClient.OSClientV3
import org.openstack4j.model.common.Identifier
import org.openstack4j.model.compute.Flavor
import org.openstack4j.model.compute.Server
import org.openstack4j.model.heat.Event
import org.openstack4j.model.heat.Stack
import org.openstack4j.model.identity.v3.Token
import org.openstack4j.model.identity.v3.User
import org.openstack4j.model.image.v2.Image
import org.openstack4j.model.storage.block.Volume
import org.openstack4j.model.storage.block.VolumeAttachment
import org.openstack4j.openstack.OSFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*
import java.util.concurrent.Executors


@Service
class OpenStackService(
    private val config: OpenStackProperties,
    private val templateService: TemplateService,
    private val client: UCloudClient
) {
    private val domainIdentifier = Identifier.byId("default")
    private val projectIdentifier = Identifier.byId(config.project.id)
    private var token: Token? = null
    private fun Token.hasExpired(): Boolean = expires.before(Date())

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
        return "${config.stackPrefix}-${ucloudJobId}"
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

    fun getImage(id: String): Image? {
        val img = getClient().imagesV2().get(id)
        logger.info("image, $img")
        return img
    }

//    fun listTemplates(): List<Template?> {
//        return getClient().heat().templates(
//    }

    fun listStacks(): List<Stack> {
        return getClient().heat().stacks().list()
    }

    fun getStack(name: String): Stack? {
        return getClient().heat().stacks().getStackByName(prefixStackName(name))
    }

    // TODO Move this to config or make sure ucloud apps matches openstack images
    fun getImage(name: String, version: String): String {
        val images = mapOf("aau-ubuntu-vm20.04" to "Ubuntu 20.04 LTS")

        return images.getOrDefault("$name$version", "")
    }

    fun createStacks(jobs: List<Job>): MutableList<Stack?> {
        logger.info("creating stacks: $jobs.size")

        val template = templateService.getTemplate("ucloud")

        val createdStacks = mutableListOf<Stack?>()

        for (job in jobs) {
            val parameters = mutableMapOf<String, String>()

            parameters["image"] = getImage(job.specification.application.name, job.specification.application.version)
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

            val missingParameters: List<String> = templateService.findMissingParameters(template, parameters)
            if (missingParameters.isNotEmpty()) {
                logger.error("Missing parameters: $missingParameters")
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing parameters: $missingParameters")
            }

            val createStack = createStack(job.id, template.templateJson, parameters)
            createdStacks.add(createStack)
        }
        return createdStacks
    }

    fun createStack(name: String, template: String, parameters: MutableMap<String, String>): Stack? {
        logger.info("Start stack: $name")

        val client = getClient()

        val build = Builders.stack()
            .template(template)
            .parameters(parameters)
            .name(prefixStackName(name))
            .timeoutMins(60)
            .build()

        // TODO Consider passing parameters as a file
        //Builders.stack().files(mutableMapOf("file1" to "file contents"))

        client.heat().stacks().create(build)
        //The stack returned by create only has id and href
        //But I guess this only returns a 200
        return getStack(prefixStackName(name))
    }

    private val threadPool = Executors.newCachedThreadPool()

    fun monitorStackCreations(jobs: List<Job>) {
        Thread.sleep(5000) // TODO Status is not set if we change this too quickly (https://github.com/SDU-eScience/UCloud/issues/2303)

        for (job in jobs) {
            threadPool.execute {
                val startTime = System.currentTimeMillis()
                while (startTime + config.monitor.timeout > System.currentTimeMillis()) {
                    val stack = getStack(job.id)
                    if (stack != null) {
                        logger.info("Verifying stack status", stack, stack.status)
                        if (stack.status == StackStatus.CREATE_COMPLETE.name) {
                            logger.info("Stack CREATE complete")
//                            JobsControl.update.call(JobsControlUpdateRequest(
//                              noget med ip her!!??
//                            ), client).orThrow()
                            JobsControl.update.call(
                                JobsControlUpdateRequest(
                                    listOf(
                                        JobsControlUpdateRequestItem(
                                            job.id,
                                            JobState.RUNNING,
                                            "Stack CREATE complete"
                                        )
                                    )
                                ),
                                client
                            ).orThrow()
                            return@execute
                        }
                    }
                    // Sleep until next retry
                    logger.info("Waiting to retry")
                    Thread.sleep(config.monitor.delay)
                }
            }
        }

    }

    fun getStackEvents(stackName: String, stackIdentity: String): MutableList<out Event>? {
        val client = getClient()
        return client.heat().events().list(stackName, stackIdentity)
    }

    fun deleteStacks(jobs: List<Job>) {
        for (job in jobs) {
            deleteStack(job.id)
        }
    }

    fun deleteStack(stackIdentity: String) {
        val client = getClient()

        val stackByName = client.heat().stacks().getStackByName(prefixStackName(stackIdentity))
        if (stackByName != null) {
            logger.info("Deleting stack: ${stackByName.name}")
            val delete = client.heat().stacks().delete(stackByName.name, stackByName.id)
            if (!delete.isSuccess) {
                throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Stack could not be deleted")
            }
        } else {
            logger.info("Stack not found: $stackIdentity")
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Stack not found")
        }
    }

    fun monitorStackDeletions(jobs: List<Job>) {
        Thread.sleep(5000) // TODO Status is not set if we change this too quickly (https://github.com/SDU-eScience/UCloud/issues/2303)

        for (job in jobs) {
            threadPool.execute {
                val startTime = System.currentTimeMillis()
                while (startTime + config.monitor.timeout > System.currentTimeMillis()) {
                    val stack = getStack(job.id)
                    if (stack == null) {
                        logger.info("Could not find stack. Assume deleted", stack)
                        JobsControl.update.call(
                            JobsControlUpdateRequest(
                                listOf(
                                    JobsControlUpdateRequestItem(
                                        job.id,
                                        JobState.SUCCESS,
                                        "Stack DELETE complete"
                                    )
                                )
                            ),
                            client
                        ).orThrow()
                        return@execute

                    }
                    logger.info("Stack found complete", stack)
                    // Sleep until next retry
                    logger.info("Waiting to retry")
                    Thread.sleep(config.monitor.delay)
                }
            }
        }

    }

    fun verifyStack(stackId: String) {
        val client = getClient()
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