package dk.aau.claaudia.openstackgateway.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("provider")
data class ProviderProperties(
    val id: String,
    val defaultProductCategory: String,
    val images: List<Image>
    ) {

    data class Image(
        val openstackId: String,
        val openstackName: String,
        val ucloudName: String,
        val ucloudVersion: String
    )
}
