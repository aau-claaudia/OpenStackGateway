package dk.aau.claaudia.openstackgateway.tasks

import dk.aau.claaudia.openstackgateway.extensions.getLogger
import dk.aau.claaudia.openstackgateway.services.OpenStackService
import org.springframework.cache.annotation.CacheEvict
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
    fun chargeAllStacks() {
        logger.info("Charge task is running")
        openStackService.chargeAllStacks()
    }

    /**
     * Sort of Heartbeat monitor that creates a log entry for splunk
     * This task is started every 5 minutes.
     * A "real" heartbeat should verify that the service is reachable, but yeah
     */
    @Scheduled(cron = "0 0/5 * * * *")
    fun heartbeat() {
        logger.info("Service is running")

        openStackService.monitorThreads();
    }

    /**
     * TO BE IMPLEMENTED
     * Should remove failed stack creations
     * This task is started every hour.
     */
    @Scheduled(cron = "0 0 * * * *")
    fun cleanUpFailedStacks() {
        logger.info("Cleanup task is running")
        openStackService.removeFailedJobs()
    }

    /**
     * Delete stacks with a shutoff instance that haven't been charged in x days
     * This task is started every hour.
     */
    @Scheduled(cron = "0 0 3 * * *")
    fun deleteNotChargedStacks() {
        logger.info("Delete not charged stacks task running")
        openStackService.deleteNotChargedStacks()
    }

    @CacheEvict(value = ["products"], allEntries = true)
    @Scheduled(cron = "0 0 3 * * *")
    fun emptyProductsCache() {
        logger.info("Empty products cache")
    }

    @Scheduled(cron = "10 0 3 * * *")
    fun refreshProductsCache() {
        logger.info("Refresh products cache")
        openStackService.retrieveProducts()
    }

    companion object {
        val logger = getLogger()
    }
}
