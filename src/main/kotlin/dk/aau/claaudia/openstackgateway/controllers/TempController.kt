package dk.aau.claaudia.openstackgateway.controllers

import dk.aau.claaudia.openstackgateway.extensions.getLogger
import dk.aau.claaudia.openstackgateway.models.requests.BulkRequest
import dk.aau.claaudia.openstackgateway.models.requests.TempJobRequest
import dk.aau.claaudia.openstackgateway.services.OpenStackService
import dk.aau.claaudia.openstackgateway.services.TemplateService
import dk.sdu.cloud.models.*
import dk.sdu.cloud.models.modified.ComputeOpenInteractiveSessionResponse
import dk.sdu.cloud.models.modified.Job
import dk.sdu.cloud.models.modified.OpenSession
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
    fun createJobs(@RequestBody job: TempJobRequest) {
        logger.info("Received job create request, $job")
        //FIXME Update when TempJobRequest changes
        if (job.request != null) {
            // Build parameters for template
            val templateVars = mutableMapOf<String, String>()

            // Add required params. Subject to change
            // FIXME it is not clear where image and flavor is found in the job request
            templateVars["image"] = job.base_image
            templateVars["flavor"] = job.machine_template.id //FIXME maybe map this from config?
            templateVars["public_keys"] = job.request_parameters.pubKey.value

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

            openstackService.createStack(job.request, testTemplate.templateJson, templateVars)
        }
    }

    @DeleteMapping("/")
    @ResponseStatus(HttpStatus.OK)
    fun deleteJobs(@RequestBody job: TempJobRequest) {
        logger.info("Received job delete request, $job")
        openstackService.deleteStack(job.request)
    }

    companion object {
        val logger = getLogger()
    }

}
