package dk.aau.claaudia.openstackgateway.controllers

import dk.aau.claaudia.openstackgateway.services.OpenStackService
import dk.sdu.cloud.providers.UCloudClient
import io.swagger.v3.oas.annotations.Hidden
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.view.RedirectView;

/**
 * This controller contains an endpoint to redirect from the base url to the swagger ui
 */
@RestController
class HomeController(
    private val client: UCloudClient,
    private val openstackService: OpenStackService
) {

    /**
     * Redirect to swagger interface
     */
    @Hidden
    @GetMapping("/")
    fun index(): RedirectView? {
        return RedirectView("/swagger-ui.html")
    }

    /**
     * THIS IS JUST FOR TESTING
     */
    @GetMapping("/charge")
    fun chargedTest(): String {
        openstackService.chargeAllStacks()
        return "charge all jobs!"
    }
}
