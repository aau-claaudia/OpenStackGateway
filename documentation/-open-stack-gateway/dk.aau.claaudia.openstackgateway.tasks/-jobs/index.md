---
title: Jobs
---
//[OpenStackGateway](../../../index.html)/[dk.aau.claaudia.openstackgateway.tasks](../index.html)/[Jobs](index.html)



# Jobs



[jvm]\
@Component



class [Jobs](index.html)(openStackService: [OpenStackService](../../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.html))

This class contains the job that are run on a schedule



## Constructors


| | |
|---|---|
| [Jobs](-jobs.html) | [jvm]<br>fun [Jobs](-jobs.html)(openStackService: [OpenStackService](../../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.html)) |


## Types


| Name | Summary |
|---|---|
| [Companion](-companion/index.html) | [jvm]<br>object [Companion](-companion/index.html) |


## Functions


| Name | Summary |
|---|---|
| [cronScheduledTask](cron-scheduled-task.html) | [jvm]<br>@Scheduled(cron = "0 0,15,30,45 * * * *")<br>fun [cronScheduledTask](cron-scheduled-task.html)()<br>Starts the task that charges jobs in ucloud. This task is started every 15 minutes. |

