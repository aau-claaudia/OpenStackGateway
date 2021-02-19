package dk.aau.claaudia.openstackgateway.services

import dk.aau.claaudia.openstackgateway.config.OpenStackProperties
import dk.aau.claaudia.openstackgateway.extensions.getLogger
import org.openstack4j.api.Builders
import org.openstack4j.api.OSClient.OSClientV3
import org.openstack4j.model.common.Identifier
import org.openstack4j.model.compute.Flavor
import org.openstack4j.model.compute.Server

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

@Service
class OpenStackService(private val config: OpenStackProperties) {
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
                    domainIdentifier)
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
        return getClient().imagesV2().list()
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

    fun createStack(name: String, template: String, parameters: MutableMap<String, String>): Stack? {
        logger.info("Start stack: $name")

        val client = getClient()

        val build = Builders.stack()
            .template(template)
            .parameters(parameters)
            .name(prefixStackName(name))
            .timeoutMins(60)
            .build()

        client.heat().stacks().create(build)
        //The stack returned by create only has id and href
        //But I guess this only return a 200
        return getStack(prefixStackName(name))
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
            .create(Builders.volume()
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
        val volumeAttachment: VolumeAttachment = volume.attachments.find { it.serverId == instance.id } ?:
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Volume not found")
        getClient().compute().servers().detachVolume(instance.id, volumeAttachment.attachmentId)
    }


    companion object {
        val logger = getLogger()
    }
}