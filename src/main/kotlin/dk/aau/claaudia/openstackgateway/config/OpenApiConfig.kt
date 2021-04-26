//package dk.aau.claaudia.openstackgateway.config
//
//import io.swagger.v3.oas.annotations.OpenAPIDefinition
//import io.swagger.v3.oas.annotations.info.Info
//import io.swagger.v3.oas.annotations.servers.Server
//import io.swagger.v3.oas.models.Components
//import io.swagger.v3.oas.models.OpenAPI
//import io.swagger.v3.oas.models.security.SecurityScheme
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//@Configuration
//@OpenAPIDefinition(
//    info = Info(
//        title = "Gateway service",
//        version = "0.0.1"
//    ),
//    servers = [Server(url = "/")],
//)
//class OpenApiConfig {
//    @Bean
//    fun openApi(): OpenAPI {
//        return OpenAPI().components(
//            Components().addSecuritySchemes("bearer-key",
//                SecurityScheme()
//                    .type(SecurityScheme.Type.HTTP)
//                    .scheme("bearer")
//                    .bearerFormat("JWT")
//            )
//        )
//    }
//}