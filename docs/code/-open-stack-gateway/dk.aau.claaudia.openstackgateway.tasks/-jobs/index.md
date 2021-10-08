//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.tasks](../index.md)/[Jobs](index.md)

# Jobs

[jvm]\
@Component

class [Jobs](index.md)(openStackService: [OpenStackService](../../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.md))

This class contains the job that are run on a schedule

## Constructors

| | |
|---|---|
| [Jobs](-jobs.md) | [jvm]<br>fun [Jobs](-jobs.md)(openStackService: [OpenStackService](../../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.md)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [cronScheduledTask](cron-scheduled-task.md) | [jvm]<br>@Scheduled(cron = "0 0,15,30,45 * * * *")<br>fun [cronScheduledTask](cron-scheduled-task.md)()<br>Starts the task that charges jobs in ucloud. This task is started every 15 minutes. |
