---
title: cronScheduledTask
---
//[OpenStackGateway](../../../index.html)/[dk.aau.claaudia.openstackgateway.tasks](../index.html)/[Jobs](index.html)/[cronScheduledTask](cron-scheduled-task.html)



# cronScheduledTask



[jvm]\




@Scheduled(cron = "0 0,15,30,45 * * * *")



fun [cronScheduledTask](cron-scheduled-task.html)()



Starts the task that charges jobs in ucloud. This task is started every 15 minutes.




