//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.interceptors](../index.md)/[UcloudRequestInterceptor](index.md)

# UcloudRequestInterceptor

[jvm]\
@Component

class [UcloudRequestInterceptor](index.md) : HandlerInterceptor

## Constructors

| | |
|---|---|
| [UcloudRequestInterceptor](-ucloud-request-interceptor.md) | [jvm]<br>constructor() |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [afterCompletion](after-completion.md) | [jvm]<br>open override fun [afterCompletion](after-completion.md)(request: HttpServletRequest, response: HttpServletResponse, handler: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), ex: [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html)?) |
| [postHandle](index.md#1311825769%2FFunctions%2F-1216412040) | [jvm]<br>open fun [postHandle](index.md#1311825769%2FFunctions%2F-1216412040)(request: HttpServletRequest, response: HttpServletResponse, handler: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), @NullablemodelAndView: ModelAndView) |
| [preHandle](pre-handle.md) | [jvm]<br>open override fun [preHandle](pre-handle.md)(request: HttpServletRequest, response: HttpServletResponse, handler: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
