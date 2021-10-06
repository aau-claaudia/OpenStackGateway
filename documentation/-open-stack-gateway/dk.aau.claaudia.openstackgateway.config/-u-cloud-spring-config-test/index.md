---
title: UCloudSpringConfigTest
---
//[OpenStackGateway](../../../index.html)/[dk.aau.claaudia.openstackgateway.config](../index.html)/[UCloudSpringConfigTest](index.html)



# UCloudSpringConfigTest



[jvm]\
@Configuration



@Profile(value = ["prod", "test", "dev", "local"])



class [UCloudSpringConfigTest](index.html)(interceptor: UCloudAuthInterceptor, requestInterceptor: [UcloudRequestInterceptor](../../dk.aau.claaudia.openstackgateway.interceptors/-ucloud-request-interceptor/index.html)) : WebMvcConfigurer



## Functions


| Name | Summary |
|---|---|
| [addArgumentResolvers](index.html#-930255598%2FFunctions%2F863300109) | [jvm]<br>open fun [addArgumentResolvers](index.html#-930255598%2FFunctions%2F863300109)(resolvers: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;HandlerMethodArgumentResolver&gt;) |
| [addCorsMappings](index.html#1262488866%2FFunctions%2F863300109) | [jvm]<br>open fun [addCorsMappings](index.html#1262488866%2FFunctions%2F863300109)(registry: CorsRegistry) |
| [addFormatters](index.html#1367850811%2FFunctions%2F863300109) | [jvm]<br>open fun [addFormatters](index.html#1367850811%2FFunctions%2F863300109)(registry: FormatterRegistry) |
| [addInterceptors](add-interceptors.html) | [jvm]<br>@Primary<br>open override fun [addInterceptors](add-interceptors.html)(registry: InterceptorRegistry) |
| [addResourceHandlers](index.html#1313948706%2FFunctions%2F863300109) | [jvm]<br>open fun [addResourceHandlers](index.html#1313948706%2FFunctions%2F863300109)(registry: ResourceHandlerRegistry) |
| [addReturnValueHandlers](index.html#637394910%2FFunctions%2F863300109) | [jvm]<br>open fun [addReturnValueHandlers](index.html#637394910%2FFunctions%2F863300109)(handlers: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;HandlerMethodReturnValueHandler&gt;) |
| [addViewControllers](index.html#-1862175630%2FFunctions%2F863300109) | [jvm]<br>open fun [addViewControllers](index.html#-1862175630%2FFunctions%2F863300109)(registry: ViewControllerRegistry) |
| [configureAsyncSupport](index.html#279135143%2FFunctions%2F863300109) | [jvm]<br>open fun [configureAsyncSupport](index.html#279135143%2FFunctions%2F863300109)(configurer: AsyncSupportConfigurer) |
| [configureContentNegotiation](index.html#927185469%2FFunctions%2F863300109) | [jvm]<br>open fun [configureContentNegotiation](index.html#927185469%2FFunctions%2F863300109)(configurer: ContentNegotiationConfigurer) |
| [configureDefaultServletHandling](index.html#-216514656%2FFunctions%2F863300109) | [jvm]<br>open fun [configureDefaultServletHandling](index.html#-216514656%2FFunctions%2F863300109)(configurer: DefaultServletHandlerConfigurer) |
| [configureHandlerExceptionResolvers](index.html#811571505%2FFunctions%2F863300109) | [jvm]<br>open fun [configureHandlerExceptionResolvers](index.html#811571505%2FFunctions%2F863300109)(resolvers: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;HandlerExceptionResolver&gt;) |
| [configureMessageConverters](index.html#1671519050%2FFunctions%2F863300109) | [jvm]<br>open fun [configureMessageConverters](index.html#1671519050%2FFunctions%2F863300109)(converters: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;HttpMessageConverter&lt;*&gt;&gt;) |
| [configurePathMatch](index.html#808521551%2FFunctions%2F863300109) | [jvm]<br>open fun [configurePathMatch](index.html#808521551%2FFunctions%2F863300109)(configurer: PathMatchConfigurer) |
| [configureViewResolvers](index.html#-2110810761%2FFunctions%2F863300109) | [jvm]<br>open fun [configureViewResolvers](index.html#-2110810761%2FFunctions%2F863300109)(registry: ViewResolverRegistry) |
| [extendHandlerExceptionResolvers](index.html#-29595133%2FFunctions%2F863300109) | [jvm]<br>open fun [extendHandlerExceptionResolvers](index.html#-29595133%2FFunctions%2F863300109)(resolvers: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;HandlerExceptionResolver&gt;) |
| [extendMessageConverters](index.html#-933776996%2FFunctions%2F863300109) | [jvm]<br>open fun [extendMessageConverters](index.html#-933776996%2FFunctions%2F863300109)(converters: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;HttpMessageConverter&lt;*&gt;&gt;) |
| [getMessageCodesResolver](index.html#-1737501503%2FFunctions%2F863300109) | [jvm]<br>@Nullable<br>open fun [getMessageCodesResolver](index.html#-1737501503%2FFunctions%2F863300109)(): MessageCodesResolver |
| [getValidator](index.html#1116410210%2FFunctions%2F863300109) | [jvm]<br>@Nullable<br>open fun [getValidator](index.html#1116410210%2FFunctions%2F863300109)(): Validator |

