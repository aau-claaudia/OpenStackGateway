package dk.aau.claaudia.openstackgateway.config

import dk.sdu.cloud.providers.UCloudClient
import org.springframework.context.annotation.Bean


import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("ucloud")
data class UCloudProperties(
    val certificate: String,
    val providerId: String,
    val refreshToken: String,
    val host: String,
    val tls: Boolean,
    val port: Int
)

@Bean
fun client(config: UCloudProperties
) = UCloudClient(config.refreshToken, config.host, config.tls, config.port)