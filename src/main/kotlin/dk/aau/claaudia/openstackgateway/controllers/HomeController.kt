package dk.aau.claaudia.openstackgateway.controllers

import dk.aau.claaudia.openstackgateway.config.ProviderProperties
import dk.aau.claaudia.openstackgateway.config.UCloudProperties
import dk.aau.claaudia.openstackgateway.services.OpenStackService
import dk.sdu.cloud.accounting.api.ProductReference
import dk.sdu.cloud.app.orchestrator.api.ComputeProductSupport
import dk.sdu.cloud.app.orchestrator.api.ComputeSupport
import dk.sdu.cloud.app.orchestrator.api.JobsProviderRetrieveProductsResponse
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
    private val uCloudProperties: UCloudProperties,
    private val providerProperties: ProviderProperties,
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

    /**
     * TESTING
     */
    @GetMapping("/products")
    fun productsTest(): JobsProviderRetrieveProductsResponse {
        return JobsProviderRetrieveProductsResponse(
            openstackService.listFlavors().filterNotNull().map { flavor ->
                ComputeProductSupport(
                    ProductReference(
                        flavor.name,
                        openstackService.getFlavorExtraSpecs(flavor.id).getOrDefault(
                            "category", providerProperties.defaultProductCategory),
                        providerProperties.id),
                    ComputeSupport(
                        ComputeSupport.Docker(
                            enabled = false,
                            web = false,
                            vnc = false,
                            logs = false,
                            terminal = false,
                            peers = false,
                            timeExtension = false,
                            utilization = false
                        ),
                        ComputeSupport.VirtualMachine(
                            enabled = true,
                            logs = false,
                            vnc = false,
                            terminal = false,
                            suspension = false,
                            timeExtension = false,
                            utilization = false
                        )
                    )
                )
            }
        )
    }
}
