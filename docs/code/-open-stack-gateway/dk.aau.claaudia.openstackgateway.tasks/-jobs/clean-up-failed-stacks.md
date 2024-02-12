//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.tasks](../index.md)/[Jobs](index.md)/[cleanUpFailedStacks](clean-up-failed-stacks.md)

# cleanUpFailedStacks

[jvm]\

@Scheduled(cron = &quot;0 7 * * * *&quot;)

fun [cleanUpFailedStacks](clean-up-failed-stacks.md)()

Remove failed stack creations This task is started every hour.
