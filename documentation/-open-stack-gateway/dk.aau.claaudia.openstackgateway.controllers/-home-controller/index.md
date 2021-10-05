//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.controllers](../index.md)/[HomeController](index.md)

# HomeController

[jvm]\
@RestController

class [HomeController](index.md)(client: UCloudClient, openstackService: [OpenStackService](../../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.md))

This controller redirects from the base url to the swagger ui

## Constructors

| | |
|---|---|
| [HomeController](-home-controller.md) | [jvm]<br>fun [HomeController](-home-controller.md)(client: UCloudClient, openstackService: [OpenStackService](../../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [chargedTest](charged-test.md) | [jvm]<br>@GetMapping(value = ["/charge"])<br>fun [chargedTest](charged-test.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [index](--index--.md) | [jvm]<br>@GetMapping(value = ["/"])<br>fun [index](--index--.md)(): RedirectView? |
