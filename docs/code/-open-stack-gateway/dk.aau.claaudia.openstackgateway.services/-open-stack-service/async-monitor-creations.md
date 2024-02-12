//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.services](../index.md)/[OpenStackService](index.md)/[asyncMonitorCreations](async-monitor-creations.md)

# asyncMonitorCreations

[jvm]\
fun [asyncMonitorCreations](async-monitor-creations.md)(jobs: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Job&gt;)

Start creation monitor function for each job in a list of jobs

The monitor function will send a status to UCloud when stack create is complete/failed
