package dk.aau.claaudia.openstackgateway.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("provider")
data class ProviderProperties(
    val id: String,
    val defaultProductCategory: String,
    //val products: List<Product>,
    val images: List<Image>
    ) {

//    data class Product(
//        val id: String,
//        val category: String,
//        val support: Support
//    )
    data class Image(
        val openstackId: String,
        val openstackName: String,
        val ucloudName: String,
        val ucloudVersion: String
    )
//    data class Support(
//        val docker: Docker,
//        val virtualMachine: VirtualMachine
//    )
//    data class Docker(
//        val enabled: Boolean? = null,
//        val logs: Boolean? = null,
//        val web: Boolean? = null,
//        val vnc: Boolean? = null,
//        val terminal: Boolean? = null,
//        val peers: Boolean? = null,
//        val timeExtension: Boolean? = null,
//        val utilization: Boolean? = null
//    )
//    data class VirtualMachine(
//        val enabled: Boolean? = null,
//        val logs: Boolean? = null,
//        val vnc: Boolean? = null,
//        val terminal: Boolean? = null,
//        val timeExtension: Boolean? = null,
//        val suspension: Boolean? = null,
//        val utilization: Boolean? = null
//    )
}
