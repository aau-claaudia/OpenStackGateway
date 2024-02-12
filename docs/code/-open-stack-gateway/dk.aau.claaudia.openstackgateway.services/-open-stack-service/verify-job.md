//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.services](../index.md)/[OpenStackService](index.md)/[verifyJob](verify-job.md)

# verifyJob

[jvm]\
fun [verifyJob](verify-job.md)(job: Job)

Verify a jobs status matches the status of the corresponding stack in openstack If no stack found in openstack, assume deleted and send status update to ucloud

If the stack status is different from expected send status update to ucloud.
