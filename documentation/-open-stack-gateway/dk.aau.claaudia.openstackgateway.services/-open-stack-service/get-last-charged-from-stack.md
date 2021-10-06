---
title: getLastChargedFromStack
---
//[OpenStackGateway](../../../index.html)/[dk.aau.claaudia.openstackgateway.services](../index.html)/[OpenStackService](index.html)/[getLastChargedFromStack](get-last-charged-from-stack.html)



# getLastChargedFromStack



[jvm]\
fun [getLastChargedFromStack](get-last-charged-from-stack.html)(stack: Stack): [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html)



Retrieve lastCharged timestamp from stack in openstack



The timestamp is saved as a text tag on the stack and includes a prefix Get this timestamp, remove prefix and parse to Instant If timestamp not found, return creation timestamp from stack




