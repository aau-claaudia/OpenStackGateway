//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.tasks](../index.md)/[Jobs](index.md)/[cronScheduledTask](cron-scheduled-task.md)

# cronScheduledTask

[jvm]\

@Scheduled(cron = "0 0,15,30,45 * * * *")

fun [cronScheduledTask](cron-scheduled-task.md)()

Starts the task that charges jobs in ucloud. This task is started every 15 minutes.
