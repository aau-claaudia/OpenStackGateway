//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.config](../index.md)/[Messages](index.md)

# Messages

[jvm]\
@ConstructorBinding

@ConfigurationProperties(value = "messages")

data class [Messages](index.md)(jobs: [Messages.Jobs](-jobs/index.md))

## Types

| Name | Summary |
|---|---|
| [Jobs](-jobs/index.md) | [jvm]<br>data class [Jobs](-jobs/index.md)(createComplete: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), createFailed: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [jobs](jobs.md) | [jvm]<br>var [jobs](jobs.md): [Messages.Jobs](-jobs/index.md) |
