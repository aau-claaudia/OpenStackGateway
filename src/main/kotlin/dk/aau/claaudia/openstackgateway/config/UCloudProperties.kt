package dk.aau.claaudia.openstackgateway.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("ucloud")
data class UCloudProperties(
    val token: String, // FIXME when implementing authentication for outgoing requests
    val secret: String, // FIXME when implementing authentication for outgoing requests
    val endpoints: Endpoints
) {
    data class Endpoints(val jobs: String)
}