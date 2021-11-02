//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.services](../index.md)/[OpenStackService](index.md)/[chargeStack](charge-stack.md)

# chargeStack

[jvm]\
fun [chargeStack](charge-stack.md)(stack: Stack)

Charge stack by calling JobControl.chargeCredits

Get the corresponding job from ucloud to make sure it exists, return if not

Calculate the time amount to charge. This amount is the time between the lastCharged timestamp and now.

Maintain lastCharged for each stack as a tag on the stack in openstack. lastCharged is the moment the stack was charged last. The first time a stack is charged, use its creation time as lastCharged
