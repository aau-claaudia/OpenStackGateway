//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.config](../index.md)/[Messages](index.md)

# Messages

[jvm]\
@ConstructorBinding

@ConfigurationProperties(value = &quot;messages&quot;)

data class [Messages](index.md)(var jobs: [Messages.Jobs](-jobs/index.md))

## Constructors

| | |
|---|---|
| [Messages](-messages.md) | [jvm]<br>constructor(jobs: [Messages.Jobs](-jobs/index.md)) |

## Types

| Name | Summary |
|---|---|
| [Jobs](-jobs/index.md) | [jvm]<br>data class [Jobs](-jobs/index.md)(val createComplete: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val createFailed: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val instanceShutdown: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val instanceRestarted: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val creditDeficit: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [jobs](jobs.md) | [jvm]<br>var [jobs](jobs.md): [Messages.Jobs](-jobs/index.md) |
