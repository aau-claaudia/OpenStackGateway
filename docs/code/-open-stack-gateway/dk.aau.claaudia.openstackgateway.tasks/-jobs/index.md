//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.tasks](../index.md)/[Jobs](index.md)

# Jobs

[jvm]\
@Component

class [Jobs](index.md)(openStackService: [OpenStackService](../../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.md))

This class contains the job that are run on a schedule

## Constructors

| | |
|---|---|
| [Jobs](-jobs.md) | [jvm]<br>constructor(openStackService: [OpenStackService](../../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.md)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [chargeAllStacks](charge-all-stacks.md) | [jvm]<br>@Scheduled(cron = &quot;0 0,15,30,45 * * * *&quot;)<br>fun [chargeAllStacks](charge-all-stacks.md)()<br>Starts the task that charges jobs in ucloud. This task is started every 15 minutes. |
| [cleanUpFailedStacks](clean-up-failed-stacks.md) | [jvm]<br>@Scheduled(cron = &quot;0 7 * * * *&quot;)<br>fun [cleanUpFailedStacks](clean-up-failed-stacks.md)()<br>Remove failed stack creations This task is started every hour. |
| [deleteNotChargedStacks](delete-not-charged-stacks.md) | [jvm]<br>@Scheduled(cron = &quot;0 0 3 * * *&quot;)<br>fun [deleteNotChargedStacks](delete-not-charged-stacks.md)()<br>Delete stacks with a shutoff instance that haven't been charged in x days This task is started every hour. |
| [emptyProductsCache](empty-products-cache.md) | [jvm]<br>@CacheEvict(value = [&quot;products&quot;], allEntries = true)<br>@Scheduled(cron = &quot;0 0 3 * * *&quot;)<br>fun [emptyProductsCache](empty-products-cache.md)() |
| [heartbeat](heartbeat.md) | [jvm]<br>@Scheduled(cron = &quot;0 0/5 * * * *&quot;)<br>fun [heartbeat](heartbeat.md)()<br>Sort of Heartbeat monitor that creates a log entry for splunk This task is started every 5 minutes. A &quot;real&quot; heartbeat should verify that the service is reachable, but yeah |
| [refreshProductsCache](refresh-products-cache.md) | [jvm]<br>@Scheduled(cron = &quot;10 0 3 * * *&quot;)<br>fun [refreshProductsCache](refresh-products-cache.md)() |
| [sendCreditDeficitUpdates](send-credit-deficit-updates.md) | [jvm]<br>@Scheduled(cron = &quot;0 0 9 ? * MON&quot;)<br>fun [sendCreditDeficitUpdates](send-credit-deficit-updates.md)()<br>Sends an update about credit deficit to currently shutoff jobs This task is started every monday morning |
