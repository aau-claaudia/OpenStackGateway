//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.tasks](../index.md)/[Jobs](index.md)/[sendCreditDeficitUpdates](send-credit-deficit-updates.md)

# sendCreditDeficitUpdates

[jvm]\

@Scheduled(cron = &quot;0 0 9 ? * MON&quot;)

fun [sendCreditDeficitUpdates](send-credit-deficit-updates.md)()

Sends an update about credit deficit to currently shutoff jobs This task is started every monday morning
