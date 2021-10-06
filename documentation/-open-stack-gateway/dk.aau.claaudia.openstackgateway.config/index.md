---
title: dk.aau.claaudia.openstackgateway.config
---
//[OpenStackGateway](../../index.html)/[dk.aau.claaudia.openstackgateway.config](index.html)



# Package dk.aau.claaudia.openstackgateway.config



## Types


| Name | Summary |
|---|---|
| [Messages](-messages/index.html) | [jvm]<br>@ConstructorBinding<br>@ConfigurationProperties(value = "messages")<br>data class [Messages](-messages/index.html)(jobs: [Messages.Jobs](-messages/-jobs/index.html)) |
| [ObjectmapperConfig](-objectmapper-config/index.html) | [jvm]<br>@Configuration<br>class [ObjectmapperConfig](-objectmapper-config/index.html) |
| [OpenApiConfig](-open-api-config/index.html) | [jvm]<br>@Configuration<br>class [OpenApiConfig](-open-api-config/index.html) |
| [OpenStackProperties](-open-stack-properties/index.html) | [jvm]<br>@ConstructorBinding<br>@ConfigurationProperties(value = "openstack")<br>data class [OpenStackProperties](-open-stack-properties/index.html)(username: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), endpoints: [OpenStackProperties.Endpoints](-open-stack-properties/-endpoints/index.html), project: [OpenStackProperties.Project](-open-stack-properties/-project/index.html), stackPrefix: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), network: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), securityGroup: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), keyName: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), availabilityZone: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), monitor: [OpenStackProperties.Monitor](-open-stack-properties/-monitor/index.html)) |
| [ProviderProperties](-provider-properties/index.html) | [jvm]<br>@ConstructorBinding<br>@ConfigurationProperties(value = "provider")<br>data class [ProviderProperties](-provider-properties/index.html)(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), products: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ProviderProperties.Product](-provider-properties/-product/index.html)&gt;, images: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[ProviderProperties.Image](-provider-properties/-image/index.html)&gt;) |
| [RequestLoggingTest](-request-logging-test/index.html) | [jvm]<br>@Configuration<br>class [RequestLoggingTest](-request-logging-test/index.html) |
| [SchedulingConfig](-scheduling-config/index.html) | [jvm]<br>@Configuration<br>@EnableScheduling<br>class [SchedulingConfig](-scheduling-config/index.html) |
| [UCloudClientConfig](-u-cloud-client-config/index.html) | [jvm]<br>@Configuration<br>class [UCloudClientConfig](-u-cloud-client-config/index.html) |
| [UCloudProperties](-u-cloud-properties/index.html) | [jvm]<br>@ConstructorBinding<br>@ConfigurationProperties(value = "ucloud")<br>data class [UCloudProperties](-u-cloud-properties/index.html)(certificate: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), providerId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), refreshToken: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), host: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), tls: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html), port: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [UCloudSpringConfigTest](-u-cloud-spring-config-test/index.html) | [jvm]<br>@Configuration<br>@Profile(value = ["prod", "test", "dev", "local"])<br>class [UCloudSpringConfigTest](-u-cloud-spring-config-test/index.html)(interceptor: UCloudAuthInterceptor, requestInterceptor: [UcloudRequestInterceptor](../dk.aau.claaudia.openstackgateway.interceptors/-ucloud-request-interceptor/index.html)) : WebMvcConfigurer |

