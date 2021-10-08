//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.services](../index.md)/[OpenStackService](index.md)

# OpenStackService

[jvm]\
@Service

class [OpenStackService](index.md)(config: [OpenStackProperties](../../dk.aau.claaudia.openstackgateway.config/-open-stack-properties/index.md), provider: [ProviderProperties](../../dk.aau.claaudia.openstackgateway.config/-provider-properties/index.md), messages: [Messages](../../dk.aau.claaudia.openstackgateway.config/-messages/index.md), templateService: [TemplateService](../-template-service/index.md), uCloudClient: UCloudClient)

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [asyncChargeDeleteJob](async-charge-delete-job.md) | [jvm]<br>fun [asyncChargeDeleteJob](async-charge-delete-job.md)(job: Job) |
| [asyncChargeJob](async-charge-job.md) | [jvm]<br>fun [asyncChargeJob](async-charge-job.md)(stack: Stack)<br>Charge a stack asynchronous to avoid exceptions stopping other stacks from being charged |
| [asyncStackCreation](async-stack-creation.md) | [jvm]<br>fun [asyncStackCreation](async-stack-creation.md)(job: Job, template: Template) |
| [attackVolumeToInstance](attack-volume-to-instance.md) | [jvm]<br>fun [attackVolumeToInstance](attack-volume-to-instance.md)(instance: Server, volume: Volume) |
| [chargeAllStacks](charge-all-stacks.md) | [jvm]<br>fun [chargeAllStacks](charge-all-stacks.md)()<br>For each stack in openstack, charge the corresponding job in ucloud. |
| [chargeDeleteJobs](charge-delete-jobs.md) | [jvm]<br>fun [chargeDeleteJobs](charge-delete-jobs.md)(jobs: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Job&gt;) |
| [chargeStack](charge-stack.md) | [jvm]<br>fun [chargeStack](charge-stack.md)(stack: Stack)<br>Charge stack by calling JobControl.chargeCredits |
| [createStack](create-stack.md) | [jvm]<br>fun [createStack](create-stack.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), template: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), parameters: [MutableMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;) |
| [createStacks](create-stacks.md) | [jvm]<br>fun [createStacks](create-stacks.md)(jobs: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Job&gt;) |
| [createVolume](create-volume.md) | [jvm]<br>fun [createVolume](create-volume.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), size: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [deattachVolumeToInstance](deattach-volume-to-instance.md) | [jvm]<br>fun [deattachVolumeToInstance](deattach-volume-to-instance.md)(instance: Server, volume: Volume) |
| [deleteJob](delete-job.md) | [jvm]<br>fun [deleteJob](delete-job.md)(job: Job) |
| [deleteVolume](delete-volume.md) | [jvm]<br>fun [deleteVolume](delete-volume.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [getFlavorById](get-flavor-by-id.md) | [jvm]<br>fun [getFlavorById](get-flavor-by-id.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): Flavor? |
| [getFlavorByName](get-flavor-by-name.md) | [jvm]<br>fun [getFlavorByName](get-flavor-by-name.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): Flavor? |
| [getImage](get-image.md) | [jvm]<br>fun [getImage](get-image.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): Image? |
| [getLastChargedFromStack](get-last-charged-from-stack.md) | [jvm]<br>fun [getLastChargedFromStack](get-last-charged-from-stack.md)(stack: Stack): [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html)<br>Retrieve lastCharged timestamp from stack in openstack |
| [getSecurityGroup](get-security-group.md) | [jvm]<br>fun [getSecurityGroup](get-security-group.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): SecurityGroup? |
| [getStackByName](get-stack-by-name.md) | [jvm]<br>fun [getStackByName](get-stack-by-name.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Stack? |
| [getStackEvents](get-stack-events.md) | [jvm]<br>fun [getStackEvents](get-stack-events.md)(stackName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), stackIdentity: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;out Event&gt;? |
| [listFlavors](list-flavors.md) | [jvm]<br>fun [listFlavors](list-flavors.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Flavor?&gt; |
| [listImages](list-images.md) | [jvm]<br>fun [listImages](list-images.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Image?&gt; |
| [listServers](list-servers.md) | [jvm]<br>fun [listServers](list-servers.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Server?&gt; |
| [listVolumes](list-volumes.md) | [jvm]<br>fun [listVolumes](list-volumes.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Volume&gt; |
| [mapImage](map-image.md) | [jvm]<br>fun [mapImage](map-image.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), version: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [monitorStackDeletions](monitor-stack-deletions.md) | [jvm]<br>fun [monitorStackDeletions](monitor-stack-deletions.md)(jobs: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Job&gt;) |
| [prepareParameters](prepare-parameters.md) | [jvm]<br>fun [prepareParameters](prepare-parameters.md)(job: Job): [MutableMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [sendJobFailedMessage](send-job-failed-message.md) | [jvm]<br>fun [sendJobFailedMessage](send-job-failed-message.md)(jobId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [sendJobRunningMessage](send-job-running-message.md) | [jvm]<br>fun [sendJobRunningMessage](send-job-running-message.md)(jobId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [sendJobStatusMessage](send-job-status-message.md) | [jvm]<br>fun [sendJobStatusMessage](send-job-status-message.md)(jobId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), state: JobState, message: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [sendStatusWhenStackComplete](send-status-when-stack-complete.md) | [jvm]<br>fun [sendStatusWhenStackComplete](send-status-when-stack-complete.md)(jobs: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Job&gt;) |
| [updateStackLastCharged](update-stack-last-charged.md) | [jvm]<br>fun [updateStackLastCharged](update-stack-last-charged.md)(listStack: Stack, chargedAt: [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html))<br>Update the lastCharged timestamp on a stack |
| [verifyJob](verify-job.md) | [jvm]<br>fun [verifyJob](verify-job.md)(job: Job) |
| [verifyJobs](verify-jobs.md) | [jvm]<br>fun [verifyJobs](verify-jobs.md)(jobs: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Job&gt;) |
