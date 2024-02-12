//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.tasks](../index.md)/[Jobs](index.md)/[chargeAllStacks](charge-all-stacks.md)

# chargeAllStacks

[jvm]\

@Scheduled(cron = &quot;0 0,15,30,45 * * * *&quot;)

fun [chargeAllStacks](charge-all-stacks.md)()

Starts the task that charges jobs in ucloud. This task is started every 15 minutes.
