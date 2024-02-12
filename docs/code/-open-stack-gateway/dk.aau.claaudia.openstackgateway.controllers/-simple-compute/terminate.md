//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.controllers](../index.md)/[SimpleCompute](index.md)/[terminate](terminate.md)

# terminate

[jvm]\
open override fun [terminate](terminate.md)(request: BulkRequest&lt;Job&gt;): BulkResponse&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)&gt;

Delete specific stacks in openstack. It is important to make sure the ucloud jobs are charged before beeing deleted. The function chargeDeleteJobs will first send a charge request to ucloud and the start the deletion process. When an async task has been started for each job, then start a task that monitors the stacks in openstack. When they can no longer be found we assume deletion and status should be sent to ucloud.
