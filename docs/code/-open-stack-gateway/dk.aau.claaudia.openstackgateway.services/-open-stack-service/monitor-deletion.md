//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.services](../index.md)/[OpenStackService](index.md)/[monitorDeletion](monitor-deletion.md)

# monitorDeletion

[jvm]\
fun [monitorDeletion](monitor-deletion.md)(job: Job)

Monitor job and send status to ucloud when jobs are found in deleted state Polls openstack continually for the corresponding stack Based on the config, have a delay between each poll and a timeout to stop polling. When the job is found in a delete complete state, send the output IP to ucloud in a job success update If this IP is not found or the stack isn't created within the timeout, don't send status
