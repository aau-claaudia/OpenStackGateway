package dk.aau.claaudia.openstackgateway.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("ucloud")
data class UCloudProperties(
    val providerId: String,
    val refreshToken: String,
    val host: String,
    val tls: Boolean,
    val port: Int
)