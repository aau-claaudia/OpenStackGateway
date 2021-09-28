package dk.aau.claaudia.openstackgateway.controllers

import dk.aau.claaudia.openstackgateway.extensions.getLogger
import dk.aau.claaudia.openstackgateway.models.requests.TempJobRequest
import dk.aau.claaudia.openstackgateway.services.OpenStackService
import dk.aau.claaudia.openstackgateway.services.TemplateService
import org.openstack4j.model.heat.Stack
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

/**
 * Temporary endpoints for doing manual creation and deletion of stacks
 */
@RestController
//@SecurityRequirement(name = "bearer-key")
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
            templateVars["public_ssh_keys"] = request.request_parameters.pubKey.value


            // GET THIS FROM CONFIG
            templateVars["network"] = "2fa0bca2-6153-4753-a8e3-ddb588d29436"
            templateVars["security_group"] = "50492808-5eb0-4a49-a6fd-163740a44f9d"
            templateVars["key_name"] = "root_openstack-master01"
            templateVars["az"] = "nova"

            val testTemplate = templateService.getTestTemplate()


            logger.info("Create stack here: $request.job_id $templateVars")
            logger.info("Template: $testTemplate")
            openstackService.createStack(request.job_id, testTemplate.templateJson, templateVars)
        }
    }

//    @DeleteMapping(value = ["/{id}"])
//    @ResponseStatus(HttpStatus.OK)
//    fun deleteJobs(@PathVariable id: String) {
//        logger.info("Received job delete request. Id: $id")
//        openstackService.deleteJob(id)
//    }

    @GetMapping(value = ["/{id}"])
    fun getStack(@PathVariable id: String): Stack? {
        logger.info("Received get stack request. Id: $id")
        return openstackService.getStackByName(id)
    }

    companion object {
        val logger = getLogger()
    }

}
