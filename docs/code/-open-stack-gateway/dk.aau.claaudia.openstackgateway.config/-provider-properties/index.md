//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.config](../index.md)/[ProviderProperties](index.md)

# ProviderProperties

[jvm]\
@ConstructorBinding

@ConfigurationProperties(value = "provider")

data class [ProviderProperties](index.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), products: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ProviderProperties.Product](-product/index.md)&gt;, images: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ProviderProperties.Image](-image/index.md)&gt;)

## Types

| Name | Summary |
|---|---|
| [Docker](-docker/index.md) | [jvm]<br>data class [Docker](-docker/index.md)(enabled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, logs: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, web: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, vnc: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, terminal: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, peers: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, timeExtension: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, utilization: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?) |
| [Image](-image/index.md) | [jvm]<br>data class [Image](-image/index.md)(openstackId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), openstackName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), ucloudName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), ucloudVersion: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [Product](-product/index.md) | [jvm]<br>data class [Product](-product/index.md)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), category: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), support: [ProviderProperties.Support](-support/index.md)) |
| [Support](-support/index.md) | [jvm]<br>data class [Support](-support/index.md)(docker: [ProviderProperties.Docker](-docker/index.md), virtualMachine: [ProviderProperties.VirtualMachine](-virtual-machine/index.md)) |
| [VirtualMachine](-virtual-machine/index.md) | [jvm]<br>data class [VirtualMachine](-virtual-machine/index.md)(enabled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, logs: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, vnc: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, terminal: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, timeExtension: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, suspension: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, utilization: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?) |

## Properties

| Name | Summary |
|---|---|
| [id](id.md) | [jvm]<br>var [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [images](images.md) | [jvm]<br>val [images](images.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ProviderProperties.Image](-image/index.md)&gt; |
| [products](products.md) | [jvm]<br>val [products](products.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ProviderProperties.Product](-product/index.md)&gt; |
