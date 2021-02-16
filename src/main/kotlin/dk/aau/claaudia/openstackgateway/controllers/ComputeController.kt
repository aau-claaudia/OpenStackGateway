package dk.aau.claaudia.openstackgateway.controllers

import dk.aau.claaudia.openstackgateway.extensions.getLogger
import dk.aau.claaudia.openstackgateway.services.OpenStackService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/flavor")
@SecurityRequirement(name = "bearer-key")
class FlavorController(private val openstackService: OpenStackService) {

    @Operation(description = "Retrieve available OpenStack flavors")
    @GetMapping("/")
    fun findAll() = openstackService.listFlavors()

    companion object {
        val logger = getLogger()
    }

}

@RestController
@RequestMapping("/api/image")
@SecurityRequirement(name = "bearer-key")
class ImageController(private val openstackService: OpenStackService) {

    @Operation(description = "Retrieve available OpenStack images")
    @GetMapping("/")
    fun findAll() = openstackService.listImages()

    companion object {
        val logger = getLogger()
    }
}
