//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.config](../index.md)/[UCloudProperties](index.md)

# UCloudProperties

[jvm]\
@ConstructorBinding

@ConfigurationProperties(value = "ucloud")

data class [UCloudProperties](index.md)(certificate: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), providerId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), refreshToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), host: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), tls: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), port: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))

## Properties

| Name | Summary |
|---|---|
| [certificate](certificate.md) | [jvm]<br>val [certificate](certificate.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [host](host.md) | [jvm]<br>val [host](host.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [port](port.md) | [jvm]<br>val [port](port.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [providerId](provider-id.md) | [jvm]<br>val [providerId](provider-id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [refreshToken](refresh-token.md) | [jvm]<br>val [refreshToken](refresh-token.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [tls](tls.md) | [jvm]<br>val [tls](tls.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
