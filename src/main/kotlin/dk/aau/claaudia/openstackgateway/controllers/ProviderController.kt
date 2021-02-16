package dk.aau.claaudia.openstackgateway.controllers

import dk.aau.claaudia.openstackgateway.extensions.getLogger
import dk.aau.claaudia.openstackgateway.models.requests.BulkRequest
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
@RequestMapping("/ucloud/claaudia/compute/jobs")
class JobController(
    private val openstackService: OpenStackService,
    private val templateService: TemplateService
) {

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    fun createJobs(@RequestBody request: BulkRequest<Job>) {
        logger.info("Received job create request, $request")
        for (job in request.items) {
            if (job.id != null) {
                logger.info("Job had no id, $job")

                // Build parameters for template
                val templateVars = mutableMapOf<String, String>()

                // Add required params. Subject to change
                // FIXME it is not clear where image and flavor is found in the job request
                templateVars["image"] = job.specification.resolvedApplication?.invocation?.tool?.tool?.description?.info?.name.toString()
                templateVars["flavor"] = job.specification.product?.id.toString() // FIXME this should be mapped from ucloud

                val testTemplate = templateService.getTestTemplate()
                val templateParameters = templateService.extractParameters(testTemplate)

                // Subject to change
                // Verify parameters are as expected. Extract params from template and compare with provided params.
                // TODO Can this be verified somewhere else? We need required template params
                val requiredParameters = templateParameters.filter{ (_, parameter) -> !parameter.containsKey("default")}.map { it.key }
                val missingParameters = requiredParameters.filter { it -> it !in templateVars.keys }
                if (missingParameters.isNotEmpty()) {
                    throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing parameters: $missingParameters")
                }

                openstackService.createStack(job.id, testTemplate.templateJson, templateVars)
            }
        }
    }

    @DeleteMapping("/")
    @ResponseStatus(HttpStatus.OK)
    fun deleteJobs(@RequestBody request: BulkRequest<Job>) {
        logger.info("Received job delete request, $request")
        //Her hiver vi hvad vi vil ud fra requesten og kaster efter openstack. WINWIN
    }

    @PostMapping("/extend")
    @ResponseStatus(HttpStatus.OK)
    fun extendJobs(@RequestBody request: BulkRequest<Job>) {
        logger.info("Received job extend request, $request")
        //Her hiver vi hvad vi vil ud fra requesten og kaster efter openstack. WINWIN
    }

    @PostMapping("/suspend")
    @ResponseStatus(HttpStatus.OK)
    fun suspendJobs(@RequestBody request: BulkRequest<Job>) {
        println("Hej suspend request, $request")
        //Her hiver vi hvad vi vil ud fra requesten og kaster efter openstack. WINWIN
    }

    @PostMapping("/verify")
    @ResponseStatus(HttpStatus.OK)
    fun verifyJobs(@RequestBody request: BulkRequest<Job>) {
        println("Hej verify request, $request")
        //Her hiver vi hvad vi vil ud fra requesten og kaster efter openstack. WINWIN
    }

    @GetMapping("/retrieveManifest")
    fun retrieveManifest(): DksducloudapporchestratorapiManifestFeatureSupport {
        println("Hej retrieve manifest request")
        // FIXME læs det her fra config
        return DksducloudapporchestratorapiManifestFeatureSupport(
            DksducloudapporchestratorapiManifestFeatureSupportCompute(
                DksducloudapporchestratorapiManifestFeatureSupportComputeDocker(
                    enabled = false,
                    web = false,
                    vnc = false,
                    batch = false,
                    logs = false,
                    terminal = false,
                    peers = false
                ),
                DksducloudapporchestratorapiManifestFeatureSupportComputeVirtualMachine(
                    enabled = true,
                    logs = true,
                    vnc = true,
                    terminal = true
                )
            )
        )
    }

    @PostMapping("/interactiveSession")
    //@ResponseStatus(HttpStatus.OK)
    fun interactiveSessionJobs(
        @RequestBody request: BulkRequest<DksducloudapporchestratorapiComputeOpenInteractiveSessionRequestItem>
    ): ComputeOpenInteractiveSessionResponse {
        println("Hej verify request, $request")

        // FIXME hiv jobs ud og returner objekter
        val shell = OpenSession.Shell(
            jobId = "jobid123",
            rank = 60,
            sessionIdentifier = "sessionid123"
        )

        val web = OpenSession.Web(
            jobId = "jobid123",
            rank = 60,
            "http://ab.dk"
        )

        return ComputeOpenInteractiveSessionResponse(listOf(shell, web))

    }

    companion object {
        val logger = getLogger()
    }
}
