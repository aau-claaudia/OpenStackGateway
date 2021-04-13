package dk.aau.claaudia.openstackgateway.controllers

import dk.aau.claaudia.openstackgateway.extensions.getLogger
import dk.aau.claaudia.openstackgateway.models.JsonTemplate
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
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean

import dk.sdu.cloud.providers.*
import kotlin.reflect.full.findParameterByName
import kotlin.reflect.full.memberProperties

/**
 * Implements the api specified in ProviderAPI.html
 */
@RestController
@SecurityRequirement(name = "bearer-key")
@RequestMapping("/ucloud/claaudia/compute/jobs")
class ProviderController(
    private val openstackService: OpenStackService,
    private val templateService: TemplateService,
    @Value("\${ucloud.refreshToken}")
    private val refreshToken: String?
) {

//    @Bean
//    fun client(
//        @Value("\${ucloud.refreshToken}")
//        refreshToken: String,
//
//        @Value("\${ucloud.host}")
//        host: String,
//
//        @Value("\${ucloud.tls:false}")
//        tls: Boolean,
//
//        @Value("\${ucloud.port:#{null}}")
//        port: Int?,
//    ) = UCloudClient(refreshToken, host, tls, port)
//


    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    fun createJobs(@RequestBody request: BulkRequest<Job>) {
        logger.info("Received job create request, $request")
        for (job in request.items) {
            if (job.id != null) {
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
                val requiredParameters = templateParameters.filter { (_, parameter) -> !parameter.containsKey("default") }.map { it.key }
                val missingParameters = requiredParameters.filter { it -> it !in templateVars.keys }
                if (missingParameters.isNotEmpty()) {
                    logger.info("Missing parameters, $missingParameters")
                    throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing parameters: $missingParameters")
                }

                openstackService.createStack(job.id, testTemplate.templateJson, templateVars)
            } else {
                logger.warn("Job had no id, $job")
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing ID on job.")
            }
        }
    }

    @PostMapping("/new/")
    @ResponseStatus(HttpStatus.OK)
    fun createJobsNew(@RequestBody request: Map<String,Any>) {
        logger.info("Recieved new job create request, $request")
        val pjobid = request["job_id"] as String
        val parameters = request["request_parameters"] as Map<String, Any>
        val sshkey = (parameters["pubKey"] as Map<String, Any>)["value"] as String

        val pbaseimage: String? = if ("base_image" in request) request["base_image"].toString() else null
        val img = if (pbaseimage != null) openstackService.getImage(pbaseimage) else null

        if (img == null) {
            val msg = "Base Image not found, $pbaseimage"
            logger.warn(msg)
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, msg)
        }

        val userDataParameters = templateService.getUserParameters(sshkey)

        val heatTemplate = templateService.getTemplate("ucloud")
        val heatParameters = templateService.getHeatParameters(heatTemplate)

        // set valid template parameters from request
        val params = mutableMapOf<String, String>()
        heatParameters.forEach {
            if (request[it.key] != null){
                params[it.key] = request[it.key].toString()
            }
        }

        // set image and user_data
        params["image"] = img.id
        params["user_data"] = userDataParameters.toString()

        openstackService.createStack("$pjobid", heatTemplate.templateJson, params)
    }

    @DeleteMapping("/delete/{jobId}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteJobs(@PathVariable jobId: String) {
        logger.info("Received job delete request, ID=$jobId")

        if (jobId == null || jobId.isEmpty()) {
            logger.warn("Delete request had no jobId!")
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing ID on delete request.")
        }

        // TODO: Before calling deleteStack, is it required to extract some metadata for the stack (running time + other)?

        openstackService.deleteStack(jobId)
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
        println(refreshToken)
        println("$refreshToken")
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
