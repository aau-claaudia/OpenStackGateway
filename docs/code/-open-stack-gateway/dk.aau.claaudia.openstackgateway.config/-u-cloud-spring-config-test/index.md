//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.config](../index.md)/[UCloudSpringConfigTest](index.md)

# UCloudSpringConfigTest

[jvm]\
@Configuration

@Profile(value = [&quot;prod&quot;, &quot;test&quot;, &quot;dev&quot;])

class [UCloudSpringConfigTest](index.md)(interceptor: UCloudAuthInterceptor, requestInterceptor: [UcloudRequestInterceptor](../../dk.aau.claaudia.openstackgateway.interceptors/-ucloud-request-interceptor/index.md)) : WebMvcConfigurer

## Constructors

| | |
|---|---|
| [UCloudSpringConfigTest](-u-cloud-spring-config-test.md) | [jvm]<br>constructor(interceptor: UCloudAuthInterceptor, requestInterceptor: [UcloudRequestInterceptor](../../dk.aau.claaudia.openstackgateway.interceptors/-ucloud-request-interceptor/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [addArgumentResolvers](index.md#-930255598%2FFunctions%2F-1216412040) | [jvm]<br>open fun [addArgumentResolvers](index.md#-930255598%2FFunctions%2F-1216412040)(resolvers: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;HandlerMethodArgumentResolver&gt;) |
| [addCorsMappings](index.md#1262488866%2FFunctions%2F-1216412040) | [jvm]<br>open fun [addCorsMappings](index.md#1262488866%2FFunctions%2F-1216412040)(registry: CorsRegistry) |
| [addFormatters](index.md#1367850811%2FFunctions%2F-1216412040) | [jvm]<br>open fun [addFormatters](index.md#1367850811%2FFunctions%2F-1216412040)(registry: FormatterRegistry) |
| [addInterceptors](add-interceptors.md) | [jvm]<br>@Primary<br>open override fun [addInterceptors](add-interceptors.md)(registry: InterceptorRegistry) |
| [addResourceHandlers](index.md#1313948706%2FFunctions%2F-1216412040) | [jvm]<br>open fun [addResourceHandlers](index.md#1313948706%2FFunctions%2F-1216412040)(registry: ResourceHandlerRegistry) |
| [addReturnValueHandlers](index.md#637394910%2FFunctions%2F-1216412040) | [jvm]<br>open fun [addReturnValueHandlers](index.md#637394910%2FFunctions%2F-1216412040)(handlers: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;HandlerMethodReturnValueHandler&gt;) |
| [addViewControllers](index.md#-1862175630%2FFunctions%2F-1216412040) | [jvm]<br>open fun [addViewControllers](index.md#-1862175630%2FFunctions%2F-1216412040)(registry: ViewControllerRegistry) |
| [configureAsyncSupport](index.md#279135143%2FFunctions%2F-1216412040) | [jvm]<br>open fun [configureAsyncSupport](index.md#279135143%2FFunctions%2F-1216412040)(configurer: AsyncSupportConfigurer) |
| [configureContentNegotiation](index.md#927185469%2FFunctions%2F-1216412040) | [jvm]<br>open fun [configureContentNegotiation](index.md#927185469%2FFunctions%2F-1216412040)(configurer: ContentNegotiationConfigurer) |
| [configureDefaultServletHandling](index.md#-216514656%2FFunctions%2F-1216412040) | [jvm]<br>open fun [configureDefaultServletHandling](index.md#-216514656%2FFunctions%2F-1216412040)(configurer: DefaultServletHandlerConfigurer) |
| [configureHandlerExceptionResolvers](index.md#811571505%2FFunctions%2F-1216412040) | [jvm]<br>open fun [configureHandlerExceptionResolvers](index.md#811571505%2FFunctions%2F-1216412040)(resolvers: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;HandlerExceptionResolver&gt;) |
| [configureMessageConverters](index.md#1671519050%2FFunctions%2F-1216412040) | [jvm]<br>open fun [configureMessageConverters](index.md#1671519050%2FFunctions%2F-1216412040)(converters: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;HttpMessageConverter&lt;*&gt;&gt;) |
| [configurePathMatch](index.md#808521551%2FFunctions%2F-1216412040) | [jvm]<br>open fun [configurePathMatch](index.md#808521551%2FFunctions%2F-1216412040)(configurer: PathMatchConfigurer) |
| [configureViewResolvers](index.md#-2110810761%2FFunctions%2F-1216412040) | [jvm]<br>open fun [configureViewResolvers](index.md#-2110810761%2FFunctions%2F-1216412040)(registry: ViewResolverRegistry) |
| [extendHandlerExceptionResolvers](index.md#-29595133%2FFunctions%2F-1216412040) | [jvm]<br>open fun [extendHandlerExceptionResolvers](index.md#-29595133%2FFunctions%2F-1216412040)(resolvers: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;HandlerExceptionResolver&gt;) |
| [extendMessageConverters](index.md#-933776996%2FFunctions%2F-1216412040) | [jvm]<br>open fun [extendMessageConverters](index.md#-933776996%2FFunctions%2F-1216412040)(converters: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;HttpMessageConverter&lt;*&gt;&gt;) |
| [getMessageCodesResolver](index.md#-1737501503%2FFunctions%2F-1216412040) | [jvm]<br>@Nullable<br>open fun [getMessageCodesResolver](index.md#-1737501503%2FFunctions%2F-1216412040)(): MessageCodesResolver |
| [getValidator](index.md#1116410210%2FFunctions%2F-1216412040) | [jvm]<br>@Nullable<br>open fun [getValidator](index.md#1116410210%2FFunctions%2F-1216412040)(): Validator |
