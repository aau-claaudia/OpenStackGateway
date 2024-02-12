//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.config](../index.md)/[OpenStackProperties](index.md)

# OpenStackProperties

[jvm]\
@ConstructorBinding

@ConfigurationProperties(value = &quot;openstack&quot;)

data class [OpenStackProperties](index.md)(val username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val endpoints: [OpenStackProperties.Endpoints](-endpoints/index.md), val project: [OpenStackProperties.Project](-project/index.md), val stackPrefix: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val network: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val securityGroup: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val keyName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val monitor: [OpenStackProperties.Monitor](-monitor/index.md), val janitor: [OpenStackProperties.Janitor](-janitor/index.md))

## Constructors

| | |
|---|---|
| [OpenStackProperties](-open-stack-properties.md) | [jvm]<br>constructor(username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), endpoints: [OpenStackProperties.Endpoints](-endpoints/index.md), project: [OpenStackProperties.Project](-project/index.md), stackPrefix: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), network: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), securityGroup: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), keyName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), monitor: [OpenStackProperties.Monitor](-monitor/index.md), janitor: [OpenStackProperties.Janitor](-janitor/index.md)) |

## Types

| Name | Summary |
|---|---|
| [Endpoints](-endpoints/index.md) | [jvm]<br>data class [Endpoints](-endpoints/index.md)(val auth: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [FlavorShutoffLifetime](-flavor-shutoff-lifetime/index.md) | [jvm]<br>data class [FlavorShutoffLifetime](-flavor-shutoff-lifetime/index.md)(val flavorName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val lifetimeDays: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)) |
| [Janitor](-janitor/index.md) | [jvm]<br>data class [Janitor](-janitor/index.md)(val deleteShutoffInstanceAfterDays: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html), val flavorLifetimes: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[OpenStackProperties.FlavorShutoffLifetime](-flavor-shutoff-lifetime/index.md)&gt;) |
| [Monitor](-monitor/index.md) | [jvm]<br>data class [Monitor](-monitor/index.md)(val timeout: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), val delay: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)) |
| [Project](-project/index.md) | [jvm]<br>data class [Project](-project/index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [endpoints](endpoints.md) | [jvm]<br>val [endpoints](endpoints.md): [OpenStackProperties.Endpoints](-endpoints/index.md) |
| [janitor](janitor.md) | [jvm]<br>val [janitor](janitor.md): [OpenStackProperties.Janitor](-janitor/index.md) |
| [keyName](key-name.md) | [jvm]<br>val [keyName](key-name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [monitor](monitor.md) | [jvm]<br>val [monitor](monitor.md): [OpenStackProperties.Monitor](-monitor/index.md) |
| [network](network.md) | [jvm]<br>val [network](network.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [password](password.md) | [jvm]<br>val [password](password.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [project](project.md) | [jvm]<br>val [project](project.md): [OpenStackProperties.Project](-project/index.md) |
| [securityGroup](security-group.md) | [jvm]<br>val [securityGroup](security-group.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [stackPrefix](stack-prefix.md) | [jvm]<br>val [stackPrefix](stack-prefix.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [username](username.md) | [jvm]<br>val [username](username.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
