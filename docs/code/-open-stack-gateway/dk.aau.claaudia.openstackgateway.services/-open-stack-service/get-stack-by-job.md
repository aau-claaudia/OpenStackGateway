//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.services](../index.md)/[OpenStackService](index.md)/[getStackByJob](get-stack-by-job.md)

# getStackByJob

[jvm]\
fun [getStackByJob](get-stack-by-job.md)(job: Job, includeDeleted: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false): Stack?

Retrieve corresponding stack in openstack of a UCloud job.

The stack name is the ucloud id prefixed based on config.
