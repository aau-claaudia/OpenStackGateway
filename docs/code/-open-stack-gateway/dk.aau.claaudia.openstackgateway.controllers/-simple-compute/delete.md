//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.controllers](../index.md)/[SimpleCompute](index.md)/[delete](delete.md)

# delete

[jvm]\
open override fun [delete](delete.md)(request: BulkRequest&lt;Job&gt;)

Delete specific stacks in openstack. It is important to make sure the ucloud jobs are charged before beeing deleted. The function chargeDeleteJobs will first send a charge request to ucloud and the start the deletion process. When an async task has been started for each job, then start a task that monitors the stacks in openstack. When they can no longer be found we assume deletion and status should be sent to ucloud.
