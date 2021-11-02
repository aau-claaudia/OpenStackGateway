//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.services](../index.md)/[OpenStackService](index.md)/[updateStackLastCharged](update-stack-last-charged.md)

# updateStackLastCharged

[jvm]\
fun [updateStackLastCharged](update-stack-last-charged.md)(listStack: Stack, chargedAt: [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html))

Update the lastCharged timestamp on a stack

The process is rather convoluted and subject to change. In order to change the tag we have to do a stack update. This requires the stack template.

Retrieve the template and remove the already saved ids. Create a stackUpdate and include the updated tag and the existing template and parameters
