package dk.aau.claaudia.openstackgateway.controllers

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.view.RedirectView;

/**
 * This controller redirects from the base url to the swagger ui
 */
@RestController
class HomeController() {

    @Hidden
    @GetMapping("/")
    fun index(): RedirectView? {
        return RedirectView("/swagger-ui.html")
    }
}
