//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.services](../index.md)/[OpenStackService](index.md)/[prepareParameters](prepare-parameters.md)

# prepareParameters

[jvm]\
fun [prepareParameters](prepare-parameters.md)(job: Job): [MutableMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;

Prepare parameters for the stack template This parses ucloud application properties to a map that matches parameters in the stack template For now, there are some hardcoded parameters since we only use a single template
