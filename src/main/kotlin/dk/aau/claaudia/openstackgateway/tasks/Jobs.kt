package dk.aau.claaudia.openstackgateway.tasks

import dk.aau.claaudia.openstackgateway.extensions.getLogger
import dk.aau.claaudia.openstackgateway.services.OpenStackService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 * This class contains the job that are run on a schedule
 *
 * @property openStackService openstack service used to do openstack related operations such as charging of stacks
 */
@Component
class Jobs(private val openStackService: OpenStackService) {
    /**
     * Starts the task that charges jobs in ucloud.
     * This task is started every 15 minutes.
     */
    @Scheduled(cron = "0 0,15,30,45 * * * *")
    fun cronScheduledTask() {
        logger.info("Charge task is running")
        openStackService.chargeAllStack()
    }

    companion object {
        val logger = getLogger()
    }
}
