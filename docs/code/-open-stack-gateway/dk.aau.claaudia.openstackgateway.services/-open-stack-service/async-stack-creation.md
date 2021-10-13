//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.services](../index.md)/[OpenStackService](index.md)/[asyncStackCreation](async-stack-creation.md)

# asyncStackCreation

[jvm]\
fun [asyncStackCreation](async-stack-creation.md)(job: Job, template: Template)

Parse parameters from the ucloud job, send parameters and template to openstack in a createStack request

Verification is done in order to detect missing required template parameters Verify that flavor, image and security group exists in openstack

If anything is missing, send a status update to ucloud with a job failed status.
