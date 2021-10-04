package dk.aau.claaudia.openstackgateway.config


import dk.aau.claaudia.openstackgateway.interceptors.UcloudRequestInterceptor
import dk.sdu.cloud.providers.UCloudAuthInterceptor
import dk.sdu.cloud.providers.UCloudClient
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.web.filter.CommonsRequestLoggingFilter
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@ConstructorBinding
@ConfigurationProperties("ucloud")
data class UCloudProperties(
    val certificate: String,
    val providerId: String,
    val refreshToken: String,
    val host: String,
    val tls: Boolean,
    val port: Int
)

//This adds an additional interceptor but cannot remove the one from the providerlibrary
@Configuration
@Profile("prod", "test", "dev", "local")
class UCloudSpringConfigTest(
    private val interceptor: UCloudAuthInterceptor,
    private val requestInterceptor: UcloudRequestInterceptor
) : WebMvcConfigurer {
    @Primary
    override fun addInterceptors(registry: InterceptorRegistry) {
        // This is disabled for test. Find a way to test authentication without getting a token from ucloud
        // Consider removing this line from provider package
        registry.addInterceptor(interceptor).addPathPatterns("/ucloud/**")

        //Use this for debugging only. It destroys the request body
        //registry.addInterceptor(requestInterceptor)//.maybe patterns here?
    }
}

// This adds logging of requests
@Configuration
class RequestLoggingTest {
    @Bean
    fun requestLoggingFilter(): CommonsRequestLoggingFilter? {
        val loggingFilter = CommonsRequestLoggingFilter()
        loggingFilter.setIncludeClientInfo(true)
        loggingFilter.setIncludeQueryString(true)
        loggingFilter.setIncludePayload(true)
        loggingFilter.setIncludeHeaders(false)
        loggingFilter.setMaxPayloadLength(64000)
        return loggingFilter
    }
}

@Configuration
class UCloudClientConfig {
    @Bean
    fun client(
        config: UCloudProperties
    ) = UCloudClient(config.refreshToken, config.host, config.tls, config.port)
}