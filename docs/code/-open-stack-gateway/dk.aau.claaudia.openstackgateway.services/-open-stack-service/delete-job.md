//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.services](../index.md)/[OpenStackService](index.md)/[deleteJob](delete-job.md)

# deleteJob

[jvm]\
fun [deleteJob](delete-job.md)(job: Job)

Takes a job and finds the corresponding stack then sends a stack delete request to openstack.

First retrieve the stack based on the id on job then send delete request with id and name from job
