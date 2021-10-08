//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.config](../index.md)/[OpenStackProperties](index.md)

# OpenStackProperties

[jvm]\
@ConstructorBinding

@ConfigurationProperties(value = "openstack")

data class [OpenStackProperties](index.md)(username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), endpoints: [OpenStackProperties.Endpoints](-endpoints/index.md), project: [OpenStackProperties.Project](-project/index.md), stackPrefix: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), network: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), securityGroup: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), keyName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), availabilityZone: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), monitor: [OpenStackProperties.Monitor](-monitor/index.md))

## Types

| Name | Summary |
|---|---|
| [Endpoints](-endpoints/index.md) | [jvm]<br>data class [Endpoints](-endpoints/index.md)(auth: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [Monitor](-monitor/index.md) | [jvm]<br>data class [Monitor](-monitor/index.md)(timeout: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), delay: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)) |
| [Project](-project/index.md) | [jvm]<br>data class [Project](-project/index.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [availabilityZone](availability-zone.md) | [jvm]<br>val [availabilityZone](availability-zone.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [endpoints](endpoints.md) | [jvm]<br>val [endpoints](endpoints.md): [OpenStackProperties.Endpoints](-endpoints/index.md) |
| [keyName](key-name.md) | [jvm]<br>val [keyName](key-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [monitor](monitor.md) | [jvm]<br>val [monitor](monitor.md): [OpenStackProperties.Monitor](-monitor/index.md) |
| [network](network.md) | [jvm]<br>val [network](network.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [password](password.md) | [jvm]<br>val [password](password.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [project](project.md) | [jvm]<br>val [project](project.md): [OpenStackProperties.Project](-project/index.md) |
| [securityGroup](security-group.md) | [jvm]<br>val [securityGroup](security-group.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [stackPrefix](stack-prefix.md) | [jvm]<br>val [stackPrefix](stack-prefix.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [username](username.md) | [jvm]<br>val [username](username.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
