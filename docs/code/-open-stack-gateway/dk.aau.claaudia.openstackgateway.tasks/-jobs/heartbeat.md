//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.tasks](../index.md)/[Jobs](index.md)/[heartbeat](heartbeat.md)

# heartbeat

[jvm]\

@Scheduled(cron = &quot;0 0/5 * * * *&quot;)

fun [heartbeat](heartbeat.md)()

Sort of Heartbeat monitor that creates a log entry for splunk This task is started every 5 minutes. A &quot;real&quot; heartbeat should verify that the service is reachable, but yeah
