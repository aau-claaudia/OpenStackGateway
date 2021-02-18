package dk.aau.claaudia.openstackgateway.controllers

import dk.aau.claaudia.openstackgateway.extensions.getLogger
import dk.aau.claaudia.openstackgateway.models.requests.TempJobRequest
import dk.aau.claaudia.openstackgateway.services.OpenStackService
import dk.aau.claaudia.openstackgateway.services.TemplateService
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/ucloud/claaudia/compute/jobs/temp")
class TempController(
    private val openstackService: OpenStackService,
    private val templateService: TemplateService
) {

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    fun createJobs(@RequestBody request: TempJobRequest) {
        logger.info("Received job create request, $request")
        //FIXME do some input validation
        if (request != null) {
            // Build parameters for template
            val templateVars = mutableMapOf<String, String>()

            templateVars["image"] = request.base_image
            templateVars["flavor"] = request.machine_template
            templateVars["public_keys"] = request.request_parameters.pubKey.value

            val testTemplate = templateService.getTestTemplate()
            val templateParameters = templateService.extractParameters(testTemplate)

            // Subject to change
            // Verify parameters are as expected. Extract params from template and compare with provided params.
            // TODO Can this be verified somewhere else? We need required template params
            val requiredParameters =
                templateParameters.filter { (_, parameter) -> !parameter.containsKey("default") }.map { it.key }
            val missingParameters = requiredParameters.filter { it -> it !in templateVars.keys }
            if (missingParameters.isNotEmpty()) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing parameters: $missingParameters")
            }

            openstackService.createStack(request.job_id, testTemplate.templateJson, templateVars)
        }
    }

    @DeleteMapping("/")
    @ResponseStatus(HttpStatus.OK)
    fun deleteJobs(@RequestBody request: TempJobRequest) {
        logger.info("Received job delete request, $request")
        openstackService.deleteStack(request.job_id)
    }

    companion object {
        val logger = getLogger()
    }

}
