package dk.aau.claaudia.openstackgateway.controllers

import io.swagger.v3.oas.annotations.Hidden
import org.springframework.context.annotation.Primary
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.view.RedirectView;
import javax.swing.text.View

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

    @GetMapping("/nogettest")
    fun indexTest(): String {
        return "Dette er bare test"
    }
}
