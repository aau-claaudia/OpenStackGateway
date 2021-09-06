package dk.aau.claaudia.openstackgateway.controllers

import dk.aau.claaudia.openstackgateway.models.StackStatus
import dk.aau.claaudia.openstackgateway.services.OpenStackService
import dk.sdu.cloud.providers.UCloudClient
import io.swagger.v3.oas.annotations.Hidden
import org.springframework.context.annotation.Primary
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.view.RedirectView;
import java.time.Instant
import javax.swing.text.View

/**
 * This controller redirects from the base url to the swagger ui
 */
@RestController
class HomeController(
    private val client: UCloudClient,
    private val openstackService: OpenStackService
) {

    @Hidden
    @GetMapping("/")
    fun index(): RedirectView? {
        return RedirectView("/swagger-ui.html")
    }

//    @GetMapping("/nogettest")
//    fun indexTest(): String {
//        return "Dette er bare test"
//    }

//    @GetMapping("/charge/{name}")
//    fun chargeTest(@PathVariable name: String): String {
//        val stack = openstackService.getStack(name) ?: return "STACK NOT FOUND"
//        openstackService.updateStackLastCharged(stack, Instant.now())
//        openstackService.chargeJob(stack)
//        return "charged!"
//    }

    @GetMapping("/charge/")
    fun chargedTest(): String {
        openstackService.chargeAllJobs()
        return "charge all jobs!"
    }

    @GetMapping("/nogettest")
    fun indexTest(): String {
        openstackService.getAllStacks()
        return "Dette er bare test"
    }
}
