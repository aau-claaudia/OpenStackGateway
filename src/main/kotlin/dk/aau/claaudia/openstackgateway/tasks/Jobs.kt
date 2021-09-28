package dk.aau.claaudia.openstackgateway.tasks

import dk.aau.claaudia.openstackgateway.extensions.getLogger
import dk.aau.claaudia.openstackgateway.services.OpenStackService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Jobs(private val openStackService: OpenStackService) {

    @Scheduled(cron = "0 0,15,30,45 * * * *")
    fun cronScheduledTask() {
        logger.info("Charge task is running")
        openStackService.chargeAllJobs()
    }

//    @Scheduled(initialDelay = 2000, fixedDelay = 3000)
//    fun initialDelayScheduledTask() {
//        logger.info("Task is running")
//    }

    companion object {
        val logger = getLogger()
    }
}
