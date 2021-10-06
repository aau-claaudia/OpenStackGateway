---
title: OpenStackService
---
//[OpenStackGateway](../../../index.html)/[dk.aau.claaudia.openstackgateway.services](../index.html)/[OpenStackService](index.html)



# OpenStackService



[jvm]\
@Service



class [OpenStackService](index.html)(config: [OpenStackProperties](../../dk.aau.claaudia.openstackgateway.config/-open-stack-properties/index.html), provider: [ProviderProperties](../../dk.aau.claaudia.openstackgateway.config/-provider-properties/index.html), messages: [Messages](../../dk.aau.claaudia.openstackgateway.config/-messages/index.html), templateService: [TemplateService](../-template-service/index.html), uCloudClient: UCloudClient)



## Types


| Name | Summary |
|---|---|
| [Companion](-companion/index.html) | [jvm]<br>object [Companion](-companion/index.html) |


## Functions


| Name | Summary |
|---|---|
| [asyncChargeDeleteJob](async-charge-delete-job.html) | [jvm]<br>fun [asyncChargeDeleteJob](async-charge-delete-job.html)(job: Job) |
| [asyncChargeJob](async-charge-job.html) | [jvm]<br>fun [asyncChargeJob](async-charge-job.html)(stack: Stack)<br>Charge a stack asynchronous to avoid exceptions stopping other stacks from being charged |
| [asyncStackCreation](async-stack-creation.html) | [jvm]<br>fun [asyncStackCreation](async-stack-creation.html)(job: Job, template: Template) |
| [attackVolumeToInstance](attack-volume-to-instance.html) | [jvm]<br>fun [attackVolumeToInstance](attack-volume-to-instance.html)(instance: Server, volume: Volume) |
| [chargeAllStacks](charge-all-stacks.html) | [jvm]<br>fun [chargeAllStacks](charge-all-stacks.html)()<br>For each stack in openstack, charge the corresponding job in ucloud. |
| [chargeDeleteJobs](charge-delete-jobs.html) | [jvm]<br>fun [chargeDeleteJobs](charge-delete-jobs.html)(jobs: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Job&gt;) |
| [chargeStack](charge-stack.html) | [jvm]<br>fun [chargeStack](charge-stack.html)(stack: Stack)<br>Charge stack by calling JobControl.chargeCredits |
| [createStack](create-stack.html) | [jvm]<br>fun [createStack](create-stack.html)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), template: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), parameters: [MutableMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) |
| [createStacks](create-stacks.html) | [jvm]<br>fun [createStacks](create-stacks.html)(jobs: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Job&gt;) |
| [createVolume](create-volume.html) | [jvm]<br>fun [createVolume](create-volume.html)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), size: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [deattachVolumeToInstance](deattach-volume-to-instance.html) | [jvm]<br>fun [deattachVolumeToInstance](deattach-volume-to-instance.html)(instance: Server, volume: Volume) |
| [deleteJob](delete-job.html) | [jvm]<br>fun [deleteJob](delete-job.html)(job: Job) |
| [deleteVolume](delete-volume.html) | [jvm]<br>fun [deleteVolume](delete-volume.html)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [getFlavorById](get-flavor-by-id.html) | [jvm]<br>fun [getFlavorById](get-flavor-by-id.html)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): Flavor? |
| [getFlavorByName](get-flavor-by-name.html) | [jvm]<br>fun [getFlavorByName](get-flavor-by-name.html)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): Flavor? |
| [getImage](get-image.html) | [jvm]<br>fun [getImage](get-image.html)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): Image? |
| [getLastChargedFromStack](get-last-charged-from-stack.html) | [jvm]<br>fun [getLastChargedFromStack](get-last-charged-from-stack.html)(stack: Stack): [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html)<br>Retrieve lastCharged timestamp from stack in openstack |
| [getSecurityGroup](get-security-group.html) | [jvm]<br>fun [getSecurityGroup](get-security-group.html)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): SecurityGroup? |
| [getStackByName](get-stack-by-name.html) | [jvm]<br>fun [getStackByName](get-stack-by-name.html)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Stack? |
| [getStackEvents](get-stack-events.html) | [jvm]<br>fun [getStackEvents](get-stack-events.html)(stackName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), stackIdentity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;out Event&gt;? |
| [listFlavors](list-flavors.html) | [jvm]<br>fun [listFlavors](list-flavors.html)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Flavor?&gt; |
| [listImages](list-images.html) | [jvm]<br>fun [listImages](list-images.html)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Image?&gt; |
| [listServers](list-servers.html) | [jvm]<br>fun [listServers](list-servers.html)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Server?&gt; |
| [listVolumes](list-volumes.html) | [jvm]<br>fun [listVolumes](list-volumes.html)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Volume&gt; |
| [mapImage](map-image.html) | [jvm]<br>fun [mapImage](map-image.html)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), version: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [monitorStackDeletions](monitor-stack-deletions.html) | [jvm]<br>fun [monitorStackDeletions](monitor-stack-deletions.html)(jobs: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Job&gt;) |
| [prepareParameters](prepare-parameters.html) | [jvm]<br>fun [prepareParameters](prepare-parameters.html)(job: Job): [MutableMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [sendJobFailedMessage](send-job-failed-message.html) | [jvm]<br>fun [sendJobFailedMessage](send-job-failed-message.html)(jobId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [sendJobRunningMessage](send-job-running-message.html) | [jvm]<br>fun [sendJobRunningMessage](send-job-running-message.html)(jobId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [sendJobStatusMessage](send-job-status-message.html) | [jvm]<br>fun [sendJobStatusMessage](send-job-status-message.html)(jobId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), state: JobState, message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [sendStatusWhenStackComplete](send-status-when-stack-complete.html) | [jvm]<br>fun [sendStatusWhenStackComplete](send-status-when-stack-complete.html)(jobs: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Job&gt;) |
| [updateStackLastCharged](update-stack-last-charged.html) | [jvm]<br>fun [updateStackLastCharged](update-stack-last-charged.html)(listStack: Stack, chargedAt: [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html))<br>Update the lastCharged timestamp on a stack |
| [verifyJob](verify-job.html) | [jvm]<br>fun [verifyJob](verify-job.html)(job: Job) |
| [verifyJobs](verify-jobs.html) | [jvm]<br>fun [verifyJobs](verify-jobs.html)(jobs: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Job&gt;) |

