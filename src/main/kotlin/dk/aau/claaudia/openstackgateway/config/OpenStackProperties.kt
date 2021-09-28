package dk.aau.claaudia.openstackgateway.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("openstack")
data class OpenStackProperties(
    val username: String,
    val password: String,
    val endpoints: Endpoints,
    val project: Project,
    val stackPrefix: String,
    val network: String,
    val securityGroup: String,
    val keyName: String,
    val availabilityZone: String,
    val monitor: Monitor
    ) {
    data class Endpoints(val auth: String)
    data class Project(val id: String,
                       val name: String)
    data class Monitor(val timeout: Int, // Timeout in milliseconds
                       val delay: Long) // Delay between each retry in milliseconds
}