//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.controllers](../index.md)/[SimpleCompute](index.md)

# SimpleCompute

[jvm]\
@RestController

class [SimpleCompute](index.md)(client: UCloudClient, openstackService: [OpenStackService](../../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.md), wsDispatcher: UCloudWsDispatcher, provider: [ProviderProperties](../../dk.aau.claaudia.openstackgateway.config/-provider-properties/index.md)) : JobsController

This class implements Uclouds JobController from the provider integration package

## Constructors

| | |
|---|---|
| [SimpleCompute](-simple-compute.md) | [jvm]<br>fun [SimpleCompute](-simple-compute.md)(client: UCloudClient, openstackService: [OpenStackService](../../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.md), wsDispatcher: UCloudWsDispatcher, provider: [ProviderProperties](../../dk.aau.claaudia.openstackgateway.config/-provider-properties/index.md)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) : Loggable |

## Functions

| Name | Summary |
|---|---|
| [canHandleWebSocketCall](index.md#-806668799%2FFunctions%2F-1216412040) | [jvm]<br>open override fun [canHandleWebSocketCall](index.md#-806668799%2FFunctions%2F-1216412040)(call: CallDescription&lt;*, *, *&gt;): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [create](create.md) | [jvm]<br>open override fun [create](create.md)(request: BulkRequest&lt;Job&gt;) |
| [delete](delete.md) | [jvm]<br>open override fun [delete](delete.md)(request: BulkRequest&lt;Job&gt;) |
| [dispatchToHandler](index.md#2087156498%2FFunctions%2F-1216412040) | [jvm]<br>open override fun &lt;[R](index.md#2087156498%2FFunctions%2F-1216412040) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), [S](index.md#2087156498%2FFunctions%2F-1216412040) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), [E](index.md#2087156498%2FFunctions%2F-1216412040) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [dispatchToHandler](index.md#2087156498%2FFunctions%2F-1216412040)(call: CallDescription&lt;[R](index.md#2087156498%2FFunctions%2F-1216412040), [S](index.md#2087156498%2FFunctions%2F-1216412040), [E](index.md#2087156498%2FFunctions%2F-1216412040)&gt;, request: [R](index.md#2087156498%2FFunctions%2F-1216412040), rawRequest: HttpServletRequest, rawResponse: HttpServletResponse): [S](index.md#2087156498%2FFunctions%2F-1216412040) |
| [dispatchToWebSocketHandler](index.md#-976524103%2FFunctions%2F-1216412040) | [jvm]<br>open override fun &lt;[R](index.md#-976524103%2FFunctions%2F-1216412040) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), [S](index.md#-976524103%2FFunctions%2F-1216412040) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), [E](index.md#-976524103%2FFunctions%2F-1216412040) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [dispatchToWebSocketHandler](index.md#-976524103%2FFunctions%2F-1216412040)(ctx: UCloudWsContext&lt;[R](index.md#-976524103%2FFunctions%2F-1216412040), [S](index.md#-976524103%2FFunctions%2F-1216412040), [E](index.md#-976524103%2FFunctions%2F-1216412040)&gt;, request: [R](index.md#-976524103%2FFunctions%2F-1216412040)) |
| [extend](extend.md) | [jvm]<br>open override fun [extend](extend.md)(request: BulkRequest&lt;JobsProviderExtendRequestItem&gt;) |
| [follow](follow.md) | [jvm]<br>open override fun [follow](follow.md)(request: JobsProviderFollowRequest, wsContext: UCloudWsContext&lt;JobsProviderFollowRequest, JobsProviderFollowResponse, CommonErrorMessage&gt;) |
| [handler](index.md#1373748360%2FFunctions%2F-1216412040) | [jvm]<br>fun [handler](index.md#1373748360%2FFunctions%2F-1216412040)(request: HttpServletRequest, response: HttpServletResponse): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [openInteractiveSession](open-interactive-session.md) | [jvm]<br>open override fun [openInteractiveSession](open-interactive-session.md)(request: BulkRequest&lt;JobsProviderOpenInteractiveSessionRequestItem&gt;): JobsProviderOpenInteractiveSessionResponse |
| [retrieveProducts](retrieve-products.md) | [jvm]<br>open override fun [retrieveProducts](retrieve-products.md)(request: [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): JobsProviderRetrieveProductsResponse |
| [retrieveUtilization](retrieve-utilization.md) | [jvm]<br>open override fun [retrieveUtilization](retrieve-utilization.md)(request: [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): JobsProviderUtilizationResponse |
| [suspend](suspend.md) | [jvm]<br>open override fun [suspend](suspend.md)(request: BulkRequest&lt;Job&gt;) |
| [verify](verify.md) | [jvm]<br>open override fun [verify](verify.md)(request: BulkRequest&lt;Job&gt;) |
