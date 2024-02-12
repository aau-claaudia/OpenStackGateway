//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.config](../index.md)/[ProviderProperties](index.md)

# ProviderProperties

[jvm]\
@ConstructorBinding

@ConfigurationProperties(value = &quot;provider&quot;)

data class [ProviderProperties](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val defaultProductCategory: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val images: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ProviderProperties.Image](-image/index.md)&gt;)

## Constructors

| | |
|---|---|
| [ProviderProperties](-provider-properties.md) | [jvm]<br>constructor(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), defaultProductCategory: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), images: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ProviderProperties.Image](-image/index.md)&gt;) |

## Types

| Name | Summary |
|---|---|
| [Image](-image/index.md) | [jvm]<br>data class [Image](-image/index.md)(val openstackId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val openstackName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val ucloudName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), val ucloudVersion: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |

## Properties

| Name | Summary |
|---|---|
| [defaultProductCategory](default-product-category.md) | [jvm]<br>val [defaultProductCategory](default-product-category.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [id](id.md) | [jvm]<br>val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [images](images.md) | [jvm]<br>val [images](images.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ProviderProperties.Image](-image/index.md)&gt; |
