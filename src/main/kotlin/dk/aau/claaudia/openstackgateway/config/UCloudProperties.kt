package dk.aau.claaudia.openstackgateway.config

import dk.sdu.cloud.providers.UCloudAuthInterceptor
import dk.sdu.cloud.providers.UCloudClient
import org.springframework.context.annotation.Bean


import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
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
//@Configuration
//class UCloudSpringConfigTest(
//    private val interceptor: UCloudAuthInterceptor,
//) : WebMvcConfigurer {
//    @Primary
//    override fun addInterceptors(registry: InterceptorRegistry) {
//        registry.addInterceptor(interceptor).addPathPatterns("/ucloud/*",)
//    }
//}


@Configuration
class UCloudClientConfig {
    @Bean
    fun client(
        config: UCloudProperties
    ) = UCloudClient(config.refreshToken, config.host, config.tls, config.port)
}