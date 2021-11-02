package dk.aau.claaudia.openstackgateway.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("messages")
data class Messages(var jobs: Jobs) {
    data class Jobs(val createComplete: String,
                    val createFailed: String)
}