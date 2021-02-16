package dk.aau.claaudia.openstackgateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Configuration
class ObjectmapperConfig {

    @Bean
    @Primary
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(KotlinModule())
        return objectMapper
    }

    @Bean(name = ["YAMLMapper"])
    fun yamlObjectMapper(): ObjectMapper {
        val mapper = ObjectMapper(YAMLFactory())
        mapper.registerModule(KotlinModule())
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
        mapper.configure(JsonParser.Feature.ALLOW_YAML_COMMENTS, true)

        return mapper
    }
}