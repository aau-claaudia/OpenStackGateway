//[OpenStackGateway](../../index.md)/[dk.aau.claaudia.openstackgateway.models](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [InstanceStatus](-instance-status/index.md) | [jvm]<br>enum [InstanceStatus](-instance-status/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[InstanceStatus](-instance-status/index.md)&gt; <br>Openstack stack status enum Based on https://github.com/openstack/nova/blob/master/nova/compute/vm_states.py |
| [JsonTemplate](-json-template/index.md) | [jvm]<br>class [JsonTemplate](-json-template/index.md)(val parameters: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;&gt;)<br>A class that partially matches a OpenStack Stack Template It can be used to parse the parameters of a template |
| [StackStatus](-stack-status/index.md) | [jvm]<br>enum [StackStatus](-stack-status/index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[StackStatus](-stack-status/index.md)&gt; <br>Openstack stack status enum Based on https://github.com/openstack/heat/blob/968969b99cf9c1cfcde4f5c5345f7bcbd4e8db08/heat/engine/stack.py#L105 |
