package dk.aau.claaudia.openstackgateway.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("openstack")
data class OpenStackProperties(var username: String, val password: String, val endpoints: Endpoints, val project: Project) {
    data class Endpoints(val auth: String)
    data class Project(val id: String, val name: String)
}