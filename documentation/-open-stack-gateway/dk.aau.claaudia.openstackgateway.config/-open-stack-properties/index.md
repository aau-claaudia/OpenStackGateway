---
title: OpenStackProperties
---
//[OpenStackGateway](../../../index.html)/[dk.aau.claaudia.openstackgateway.config](../index.html)/[OpenStackProperties](index.html)



# OpenStackProperties



[jvm]\
@ConstructorBinding



@ConfigurationProperties(value = "openstack")



data class [OpenStackProperties](index.html)(username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), endpoints: [OpenStackProperties.Endpoints](-endpoints/index.html), project: [OpenStackProperties.Project](-project/index.html), stackPrefix: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), network: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), securityGroup: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), keyName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), availabilityZone: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), monitor: [OpenStackProperties.Monitor](-monitor/index.html))



## Types


| Name | Summary |
|---|---|
| [Endpoints](-endpoints/index.html) | [jvm]<br>data class [Endpoints](-endpoints/index.html)(auth: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [Monitor](-monitor/index.html) | [jvm]<br>data class [Monitor](-monitor/index.html)(timeout: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), delay: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)) |
| [Project](-project/index.html) | [jvm]<br>data class [Project](-project/index.html)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |


## Properties


| Name | Summary |
|---|---|
| [availabilityZone](availability-zone.html) | [jvm]<br>val [availabilityZone](availability-zone.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [endpoints](endpoints.html) | [jvm]<br>val [endpoints](endpoints.html): [OpenStackProperties.Endpoints](-endpoints/index.html) |
| [keyName](key-name.html) | [jvm]<br>val [keyName](key-name.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [monitor](monitor.html) | [jvm]<br>val [monitor](monitor.html): [OpenStackProperties.Monitor](-monitor/index.html) |
| [network](network.html) | [jvm]<br>val [network](network.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [password](password.html) | [jvm]<br>val [password](password.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [project](project.html) | [jvm]<br>val [project](project.html): [OpenStackProperties.Project](-project/index.html) |
| [securityGroup](security-group.html) | [jvm]<br>val [securityGroup](security-group.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [stackPrefix](stack-prefix.html) | [jvm]<br>val [stackPrefix](stack-prefix.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [username](username.html) | [jvm]<br>val [username](username.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

