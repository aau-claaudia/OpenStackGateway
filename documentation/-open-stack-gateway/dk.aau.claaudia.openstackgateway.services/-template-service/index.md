---
title: TemplateService
---
//[OpenStackGateway](../../../index.html)/[dk.aau.claaudia.openstackgateway.services](../index.html)/[TemplateService](index.html)



# TemplateService



[jvm]\
@Service



class [TemplateService](index.html)(@Qualifier(value = "YAMLMapper")mapper: ObjectMapper)

This class contains utility functions for loading and verification of heat templates



## Constructors


| | |
|---|---|
| [TemplateService](-template-service.html) | [jvm]<br>fun [TemplateService](-template-service.html)(@Qualifier(value = "YAMLMapper")mapper: ObjectMapper) |


## Types


| Name | Summary |
|---|---|
| [Companion](-companion/index.html) | [jvm]<br>object [Companion](-companion/index.html) |


## Functions


| Name | Summary |
|---|---|
| [extractParameters](extract-parameters.html) | [jvm]<br>fun [extractParameters](extract-parameters.html)(template: Template): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;&gt; |
| [findMissingParameters](find-missing-parameters.html) | [jvm]<br>fun [findMissingParameters](find-missing-parameters.html)(template: Template, providedParameters: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [getTemplate](get-template.html) | [jvm]<br>fun [getTemplate](get-template.html)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Template |
| [getTestTemplate](get-test-template.html) | [jvm]<br>fun [getTestTemplate](get-test-template.html)(): Template |
| [validateTemplate](validate-template.html) | [jvm]<br>fun [validateTemplate](validate-template.html)(template: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |


## Properties


| Name | Summary |
|---|---|
| [templates](templates.html) | [jvm]<br>val [templates](templates.html): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |

