package dk.aau.claaudia.openstackgateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class UCloudClientConfig(private val config: UCloudProperties) {

    @Bean
    fun createWebClient(): WebClient {
        //FIXME Add authentication for outgoing requests here
        return WebClient.create(config.endpoints.jobs)
    }

//    @Bean
//    fun authorizedClientManager(
//        clientRegistrationRepository: ClientRegistrationRepository?,
//        authorizedClientRepository: OAuth2AuthorizedClientRepository?
//    ): OAuth2AuthorizedClientManager? {
//        val authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
//            .clientCredentials()
//            .build()
//        val authorizedClientManager = DefaultOAuth2AuthorizedClientManager(
//            clientRegistrationRepository, authorizedClientRepository
//        )
//        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider)
//        return authorizedClientManager
//    }
//
//    @Bean
//    fun webClient(authorizedClientManager: OAuth2AuthorizedClientManager?): WebClient? {
//        val oauth2Client = ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager)
//        oauth2Client.setDefaultClientRegistrationId("ucloud")
//        return WebClient.builder()
//            .apply(oauth2Client.oauth2Configuration())
//            .build()
//    }
}