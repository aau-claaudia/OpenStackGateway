//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.services](../index.md)/[TemplateService](index.md)

# TemplateService

[jvm]\
@Service

class [TemplateService](index.md)(@Qualifier(value = "YAMLMapper")mapper: ObjectMapper)

This class contains utility functions for loading and verification of heat templates

## Constructors

| | |
|---|---|
| [TemplateService](-template-service.md) | [jvm]<br>fun [TemplateService](-template-service.md)(@Qualifier(value = "YAMLMapper")mapper: ObjectMapper) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [extractParameters](extract-parameters.md) | [jvm]<br>fun [extractParameters](extract-parameters.md)(template: Template): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt;&gt; |
| [findMissingParameters](find-missing-parameters.md) | [jvm]<br>fun [findMissingParameters](find-missing-parameters.md)(template: Template, providedParameters: [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
| [getTemplate](get-template.md) | [jvm]<br>fun [getTemplate](get-template.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Template |
| [getTestTemplate](get-test-template.md) | [jvm]<br>fun [getTestTemplate](get-test-template.md)(): Template |
| [validateTemplate](validate-template.md) | [jvm]<br>fun [validateTemplate](validate-template.md)(template: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

## Properties

| Name | Summary |
|---|---|
| [templates](templates.md) | [jvm]<br>val [templates](templates.md): [Map](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-map/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt; |
