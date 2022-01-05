package dk.aau.claaudia.openstackgateway.extensions

import dk.aau.claaudia.openstackgateway.config.OpenStackProperties
import dk.sdu.cloud.app.orchestrator.api.Job
import org.openstack4j.model.heat.Stack
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.lang.invoke.MethodHandles
import javax.annotation.PostConstruct

inline fun getLogger(): Logger {
    return LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
}

/**
 * Allow extension functions to use config
 */
@Component
class ExtensionConfig (openStackProperties: OpenStackProperties) {
    val config = openStackProperties

    @PostConstruct
    private fun init() {
        c = this
    }
}

private lateinit var c: ExtensionConfig

/**
 * Given a stack, remove the name prefix to get ucloud id
 */
val Stack.ucloudId: String
    get() = this.name.removePrefix(c.config.stackPrefix)

/**
 * Given a ucloud job, add prefix to id to get openstack name
 */
val Job.openstackName: String
    get() = "${c.config.stackPrefix}${this.id}"


