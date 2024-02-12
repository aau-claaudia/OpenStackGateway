//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.services](../index.md)/[OpenStackService](index.md)/[asyncMonitorDeletions](async-monitor-deletions.md)

# asyncMonitorDeletions

[jvm]\
fun [asyncMonitorDeletions](async-monitor-deletions.md)(jobs: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Job&gt;)

Start deletion monitor function for each job in a list of jobs

The monitor function will send a status to UCloud when stack is found in deleted status
