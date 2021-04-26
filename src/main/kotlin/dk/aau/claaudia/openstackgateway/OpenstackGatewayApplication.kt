package dk.aau.claaudia.openstackgateway

import dk.aau.claaudia.openstackgateway.config.OpenStackProperties
import dk.aau.claaudia.openstackgateway.config.UCloudProperties
import dk.sdu.cloud.providers.UCloudClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EnableConfigurationProperties(OpenStackProperties::class, UCloudProperties::class)
@ComponentScan("dk.aau.claaudia.openstackgateway", "dk.sdu.cloud.providers")
class OpenstackGatewayApplication{
    @Bean
    fun client(config: UCloudProperties
    ) = UCloudClient(config.refreshToken, config.host, config.tls, config.port)
}

fun main(args: Array<String>) {
    runApplication<OpenstackGatewayApplication>(*args)
}
