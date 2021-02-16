package dk.aau.claaudia.openstackgateway.services;

import com.fasterxml.jackson.databind.ObjectMapper
import dk.aau.claaudia.openstackgateway.extensions.getLogger
import dk.aau.claaudia.openstackgateway.models.JsonTemplate
import org.openstack4j.api.Builders
import org.openstack4j.model.heat.Template
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

/**
 * This class contains utility functions for loading and verification of heat templates
 *
 */
@Service
public class TemplateService(
    @Qualifier("YAMLMapper")
    private val mapper: ObjectMapper
) {

    val templates = mapOf(
        "simple-stack" to "/heat-templates/simple-stack.yaml"
    )

    fun getTemplate(name: String): Template {
        val template = this::class.java.getResource(templates[name]).readText(Charsets.UTF_8)
        validateTemplate(template)

        return Builders.template().templateJson(template).build()
    }

    fun getTestTemplate(): Template {
        val template = this::class.java.getResource("/heat-templates/simple-stack.yaml").readText(Charsets.UTF_8)
        validateTemplate(template)

        return Builders.template().templateJson(template).build()
    }

    fun validateTemplate(template: String): Boolean {
        // TODO: Implement this function
        // The idea of this function is to validate the heat template by calling openstacks verify endpoint
        // We need to inject the openstackservice here
        // FIXME testing revealed difficulties in verifying template even though they are correct
        //val validateTemplate = client.heat().templates().validateTemplate(template)

        //if (!validateTemplate.isValid) {
        //    logger.error("invalid template ${validateTemplate.message}")
        //    //throw ResponseStatusException(HttpStatus.BAD_REQUEST, validateTemplate.message)
        //}

        return true
    }

    // TODO move these util functions somewhere else. Consider an extension function on heat Template??
    fun extractParameters(template: Template): Map<String, Map<String, String>> {
        val t: JsonTemplate = mapper.readValue(template.templateJson, JsonTemplate::class.java)
        logger.info("template parameters: ", t.parameters)

        return t.parameters
    }

    companion object {
        val logger = getLogger()
    }
}
