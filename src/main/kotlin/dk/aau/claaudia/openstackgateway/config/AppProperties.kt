//package dk.aau.claaudia.openstackgateway.config
//
//import org.springframework.boot.context.properties.ConfigurationProperties
//import org.springframework.boot.context.properties.ConstructorBinding
//
//
//@ConstructorBinding
//@ConfigurationProperties("app")
//data class AppProperties(val security: Security) {
//    data class Security(val incoming: Incoming, val outgoing: Outgoing)
//    data class Incoming(
//        val publicKey: String,
//        val tokenPrefix: String,
//        val headerString: String
//    )
//
//    data class Outgoing(val privateKey: String)
//}