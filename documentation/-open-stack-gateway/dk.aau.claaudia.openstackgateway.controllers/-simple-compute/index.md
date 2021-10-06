---
title: SimpleCompute
---
//[OpenStackGateway](../../../index.html)/[dk.aau.claaudia.openstackgateway.controllers](../index.html)/[SimpleCompute](index.html)



# SimpleCompute



[jvm]\
@RestController



class [SimpleCompute](index.html)(client: UCloudClient, openstackService: [OpenStackService](../../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.html), wsDispatcher: UCloudWsDispatcher, provider: [ProviderProperties](../../dk.aau.claaudia.openstackgateway.config/-provider-properties/index.html)) : JobsController



## Types


| Name | Summary |
|---|---|
| [Companion](-companion/index.html) | [jvm]<br>object [Companion](-companion/index.html) : Loggable |


## Functions


| Name | Summary |
|---|---|
| [canHandleWebSocketCall](index.html#-806668799%2FFunctions%2F863300109) | [jvm]<br>open override fun [canHandleWebSocketCall](index.html#-806668799%2FFunctions%2F863300109)(call: CallDescription&lt;*, *, *&gt;): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [create](create.html) | [jvm]<br>open override fun [create](create.html)(request: BulkRequest&lt;Job&gt;) |
| [delete](delete.html) | [jvm]<br>open override fun [delete](delete.html)(request: BulkRequest&lt;Job&gt;) |
| [dispatchToHandler](index.html#2087156498%2FFunctions%2F863300109) | [jvm]<br>open override fun &lt;[R](index.html#2087156498%2FFunctions%2F863300109) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), [S](index.html#2087156498%2FFunctions%2F863300109) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), [E](index.html#2087156498%2FFunctions%2F863300109) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [dispatchToHandler](index.html#2087156498%2FFunctions%2F863300109)(call: CallDescription&lt;[R](index.html#2087156498%2FFunctions%2F863300109), [S](index.html#2087156498%2FFunctions%2F863300109), [E](index.html#2087156498%2FFunctions%2F863300109)&gt;, request: [R](index.html#2087156498%2FFunctions%2F863300109), rawRequest: HttpServletRequest, rawResponse: HttpServletResponse): [S](index.html#2087156498%2FFunctions%2F863300109) |
| [dispatchToWebSocketHandler](index.html#-976524103%2FFunctions%2F863300109) | [jvm]<br>open override fun &lt;[R](index.html#-976524103%2FFunctions%2F863300109) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), [S](index.html#-976524103%2FFunctions%2F863300109) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html), [E](index.html#-976524103%2FFunctions%2F863300109) : [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)&gt; [dispatchToWebSocketHandler](index.html#-976524103%2FFunctions%2F863300109)(ctx: UCloudWsContext&lt;[R](index.html#-976524103%2FFunctions%2F863300109), [S](index.html#-976524103%2FFunctions%2F863300109), [E](index.html#-976524103%2FFunctions%2F863300109)&gt;, request: [R](index.html#-976524103%2FFunctions%2F863300109)) |
| [extend](extend.html) | [jvm]<br>open override fun [extend](extend.html)(request: BulkRequest&lt;JobsProviderExtendRequestItem&gt;) |
| [follow](follow.html) | [jvm]<br>open override fun [follow](follow.html)(request: JobsProviderFollowRequest, wsContext: UCloudWsContext&lt;JobsProviderFollowRequest, JobsProviderFollowResponse, CommonErrorMessage&gt;) |
| [handler](index.html#1373748360%2FFunctions%2F863300109) | [jvm]<br>fun [handler](index.html#1373748360%2FFunctions%2F863300109)(request: HttpServletRequest, response: HttpServletResponse): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [openInteractiveSession](open-interactive-session.html) | [jvm]<br>open override fun [openInteractiveSession](open-interactive-session.html)(request: BulkRequest&lt;JobsProviderOpenInteractiveSessionRequestItem&gt;): JobsProviderOpenInteractiveSessionResponse |
| [retrieveProducts](retrieve-products.html) | [jvm]<br>open override fun [retrieveProducts](retrieve-products.html)(request: [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): JobsProviderRetrieveProductsResponse |
| [retrieveUtilization](retrieve-utilization.html) | [jvm]<br>open override fun [retrieveUtilization](retrieve-utilization.html)(request: [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): JobsProviderUtilizationResponse |
| [suspend](suspend.html) | [jvm]<br>open override fun [suspend](suspend.html)(request: BulkRequest&lt;Job&gt;) |
| [verify](verify.html) | [jvm]<br>open override fun [verify](verify.html)(request: BulkRequest&lt;Job&gt;) |

