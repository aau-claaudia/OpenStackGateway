//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.services](../index.md)/[OpenStackService](index.md)/[createStack](create-stack.md)

# createStack

[jvm]\
fun [createStack](create-stack.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), template: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), parameters: [MutableMap](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;)

Send a stack creation request to openstack. Use the openstack4j builder to create the request and send it to openstack We don't return anything here because, since tasks are started to monitor the creation.
