//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.services](../index.md)/[OpenStackService](index.md)/[sendStatusWhenStackComplete](send-status-when-stack-complete.md)

# sendStatusWhenStackComplete

[jvm]\
fun [sendStatusWhenStackComplete](send-status-when-stack-complete.md)(jobs: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Job&gt;)

Monitor a list of jobs and send status to ucloud when jobs are running or failed

For each job start an async task that polls openstack for the corresponding stack Based on the config, have a delay between each poll and a timeout to stop polling. When the job is found in a create complete state, send the output IP to ucloud in a job success update If this IP is not found or the stack isnt created within the timeout, send a job failed update
