package dk.aau.claaudia.openstackgateway.services;

import com.fasterxml.jackson.databind.ObjectMapper
import dk.aau.claaudia.openstackgateway.extensions.getLogger
import dk.aau.claaudia.openstackgateway.models.JsonTemplate
import org.openstack4j.api.Builders
//import org.openstack4j.model.heat.Template
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
public class TemplateService(
    @Qualifier("YAMLMapper")
    private val mapper: ObjectMapper
) {

    fun getTestTemplate(): org.openstack4j.model.heat.Template {
        val template = this::class.java.getResource("/heat-templates/simple-stack.yaml").readText(Charsets.UTF_8)

        // Ryk openstack clienten ud så den kan hentes som en bean og bruges til validering her måske?
        // FIXME der er et eller andet galt med valideringen
        // FIXME der skal måske laves et globalt logger object som bean?
        //val validateTemplate = client.heat().templates().validateTemplate(template)

//        if (!validateTemplate.isValid) {
//            logger.error("invalid template ${validateTemplate.message}")
//            //throw ResponseStatusException(HttpStatus.BAD_REQUEST, validateTemplate.message)
//        }

        return Builders.template().templateJson(template).build()
    }

    // TODO move these util functions somewhere else. Consider an extension function on heat Template??
    fun extractParameters(template: org.openstack4j.model.heat.Template): Map<String, Map<String, String>> {
        val t: JsonTemplate = mapper.readValue(template.templateJson, JsonTemplate::class.java)
        logger.info("template parameters: ", t.parameters)

        return t.parameters
    }

    companion object {
        val logger = getLogger()
    }
}
