---
title: ProviderProperties
---
//[OpenStackGateway](../../../index.html)/[dk.aau.claaudia.openstackgateway.config](../index.html)/[ProviderProperties](index.html)



# ProviderProperties



[jvm]\
@ConstructorBinding



@ConfigurationProperties(value = "provider")



data class [ProviderProperties](index.html)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), products: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ProviderProperties.Product](-product/index.html)&gt;, images: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ProviderProperties.Image](-image/index.html)&gt;)



## Types


| Name | Summary |
|---|---|
| [Docker](-docker/index.html) | [jvm]<br>data class [Docker](-docker/index.html)(enabled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, logs: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, web: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, vnc: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, terminal: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, peers: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, timeExtension: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, utilization: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?) |
| [Image](-image/index.html) | [jvm]<br>data class [Image](-image/index.html)(openstackId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), openstackName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), ucloudName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), ucloudVersion: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) |
| [Product](-product/index.html) | [jvm]<br>data class [Product](-product/index.html)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), category: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), support: [ProviderProperties.Support](-support/index.html)) |
| [Support](-support/index.html) | [jvm]<br>data class [Support](-support/index.html)(docker: [ProviderProperties.Docker](-docker/index.html), virtualMachine: [ProviderProperties.VirtualMachine](-virtual-machine/index.html)) |
| [VirtualMachine](-virtual-machine/index.html) | [jvm]<br>data class [VirtualMachine](-virtual-machine/index.html)(enabled: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, logs: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, vnc: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, terminal: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, timeExtension: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, suspension: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?, utilization: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)?) |


## Properties


| Name | Summary |
|---|---|
| [id](id.html) | [jvm]<br>var [id](id.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [images](images.html) | [jvm]<br>val [images](images.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ProviderProperties.Image](-image/index.html)&gt; |
| [products](products.html) | [jvm]<br>val [products](products.html): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ProviderProperties.Product](-product/index.html)&gt; |

