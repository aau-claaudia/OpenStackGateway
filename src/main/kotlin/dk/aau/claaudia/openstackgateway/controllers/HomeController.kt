package dk.aau.claaudia.openstackgateway.controllers

import dk.aau.claaudia.openstackgateway.extensions.getLogger
import io.swagger.v3.oas.annotations.Hidden
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

/**
 * This controller contains an endpoint to redirect from the base url to the swagger ui
 */
@RestController
class HomeController(
) {

    /**
     * Redirect to swagger interface
     */
    @Hidden
    @GetMapping("/")
    fun index(): RedirectView? {
        return RedirectView("/swagger-ui.html")
    }

    companion object {
        val logger = getLogger()
    }
}
