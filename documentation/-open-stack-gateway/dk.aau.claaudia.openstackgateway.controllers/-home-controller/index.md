---
title: HomeController
---
//[OpenStackGateway](../../../index.html)/[dk.aau.claaudia.openstackgateway.controllers](../index.html)/[HomeController](index.html)



# HomeController



[jvm]\
@RestController



class [HomeController](index.html)(client: UCloudClient, openstackService: [OpenStackService](../../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.html))

This controller redirects from the base url to the swagger ui



## Constructors


| | |
|---|---|
| [HomeController](-home-controller.html) | [jvm]<br>fun [HomeController](-home-controller.html)(client: UCloudClient, openstackService: [OpenStackService](../../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.html)) |


## Functions


| Name | Summary |
|---|---|
| [chargedTest](charged-test.html) | [jvm]<br>@GetMapping(value = ["/charge"])<br>fun [chargedTest](charged-test.html)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [index](--index--.html) | [jvm]<br>@GetMapping(value = ["/"])<br>fun [index](--index--.html)(): RedirectView? |

