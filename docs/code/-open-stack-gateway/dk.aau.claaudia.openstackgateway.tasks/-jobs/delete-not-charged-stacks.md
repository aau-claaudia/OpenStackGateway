//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.tasks](../index.md)/[Jobs](index.md)/[deleteNotChargedStacks](delete-not-charged-stacks.md)

# deleteNotChargedStacks

[jvm]\

@Scheduled(cron = &quot;0 0 3 * * *&quot;)

fun [deleteNotChargedStacks](delete-not-charged-stacks.md)()

Delete stacks with a shutoff instance that haven't been charged in x days This task is started every hour.
