//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.services](../index.md)/[OpenStackService](index.md)/[monitorCreation](monitor-creation.md)

# monitorCreation

[jvm]\
fun [monitorCreation](monitor-creation.md)(job: Job)

Monitor job and send status to ucloud when jobs are running or failed Polls openstack continually for the corresponding stack Based on the config, have a delay between each poll and a timeout to stop polling. When the job is found in a create complete state, send the output IP to ucloud in a job running update If this IP is not found or the stack isnt created within the timeout, send a job failed update
