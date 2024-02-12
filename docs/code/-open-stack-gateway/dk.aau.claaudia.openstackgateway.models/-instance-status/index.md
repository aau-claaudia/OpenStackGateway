//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.models](../index.md)/[InstanceStatus](index.md)

# InstanceStatus

[jvm]\
enum [InstanceStatus](index.md) : [Enum](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-enum/index.html)&lt;[InstanceStatus](index.md)&gt; 

Openstack stack status enum Based on https://github.com/openstack/nova/blob/master/nova/compute/vm_states.py

## Entries

| | |
|---|---|
| [ACTIVE](-a-c-t-i-v-e/index.md) | [jvm]<br>[ACTIVE](-a-c-t-i-v-e/index.md) |
| [BUILDING](-b-u-i-l-d-i-n-g/index.md) | [jvm]<br>[BUILDING](-b-u-i-l-d-i-n-g/index.md) |
| [PAUSED](-p-a-u-s-e-d/index.md) | [jvm]<br>[PAUSED](-p-a-u-s-e-d/index.md) |
| [SUSPENDED](-s-u-s-p-e-n-d-e-d/index.md) | [jvm]<br>[SUSPENDED](-s-u-s-p-e-n-d-e-d/index.md) |
| [STOPPED](-s-t-o-p-p-e-d/index.md) | [jvm]<br>[STOPPED](-s-t-o-p-p-e-d/index.md) |
| [RESCUED](-r-e-s-c-u-e-d/index.md) | [jvm]<br>[RESCUED](-r-e-s-c-u-e-d/index.md) |
| [RESIZED](-r-e-s-i-z-e-d/index.md) | [jvm]<br>[RESIZED](-r-e-s-i-z-e-d/index.md) |
| [SOFT_DELETED](-s-o-f-t_-d-e-l-e-t-e-d/index.md) | [jvm]<br>[SOFT_DELETED](-s-o-f-t_-d-e-l-e-t-e-d/index.md) |
| [DELETED](-d-e-l-e-t-e-d/index.md) | [jvm]<br>[DELETED](-d-e-l-e-t-e-d/index.md) |
| [ERROR](-e-r-r-o-r/index.md) | [jvm]<br>[ERROR](-e-r-r-o-r/index.md) |
| [SHELVED](-s-h-e-l-v-e-d/index.md) | [jvm]<br>[SHELVED](-s-h-e-l-v-e-d/index.md) |
| [SHELVED_OFFLOADED](-s-h-e-l-v-e-d_-o-f-f-l-o-a-d-e-d/index.md) | [jvm]<br>[SHELVED_OFFLOADED](-s-h-e-l-v-e-d_-o-f-f-l-o-a-d-e-d/index.md) |

## Properties

| Name | Summary |
|---|---|
| [name](../-stack-status/-r-e-s-t-o-r-e_-f-a-i-l-e-d/index.md#-372974862%2FProperties%2F-1216412040) | [jvm]<br>val [name](../-stack-status/-r-e-s-t-o-r-e_-f-a-i-l-e-d/index.md#-372974862%2FProperties%2F-1216412040): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [ordinal](../-stack-status/-r-e-s-t-o-r-e_-f-a-i-l-e-d/index.md#-739389684%2FProperties%2F-1216412040) | [jvm]<br>val [ordinal](../-stack-status/-r-e-s-t-o-r-e_-f-a-i-l-e-d/index.md#-739389684%2FProperties%2F-1216412040): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |

## Functions

| Name | Summary |
|---|---|
| [valueOf](value-of.md) | [jvm]<br>fun [valueOf](value-of.md)(value: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [InstanceStatus](index.md)<br>Returns the enum constant of this type with the specified name. The string must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.) |
| [values](values.md) | [jvm]<br>fun [values](values.md)(): [Array](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-array/index.html)&lt;[InstanceStatus](index.md)&gt;<br>Returns an array containing the constants of this enum type, in the order they're declared. |
