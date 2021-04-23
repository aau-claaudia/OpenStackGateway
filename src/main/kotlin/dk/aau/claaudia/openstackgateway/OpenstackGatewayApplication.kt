package dk.aau.claaudia.openstackgateway

import dk.aau.claaudia.openstackgateway.config.AppProperties
import dk.aau.claaudia.openstackgateway.config.OpenStackProperties
import dk.aau.claaudia.openstackgateway.config.UCloudProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class, OpenStackProperties::class, UCloudProperties::class)
@ComponentScan("dk.aau.claaudia.openstackgateway", "dk.sdu.cloud.providers")
class OpenstackGatewayApplication

fun main(args: Array<String>) {
    runApplication<OpenstackGatewayApplication>(*args)
}
