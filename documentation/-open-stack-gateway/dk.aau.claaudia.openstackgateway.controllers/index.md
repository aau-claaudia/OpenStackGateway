---
title: dk.aau.claaudia.openstackgateway.controllers
---
//[OpenStackGateway](../../index.html)/[dk.aau.claaudia.openstackgateway.controllers](index.html)



# Package dk.aau.claaudia.openstackgateway.controllers



## Types


| Name | Summary |
|---|---|
| [HomeController](-home-controller/index.html) | [jvm]<br>@RestController<br>class [HomeController](-home-controller/index.html)(client: UCloudClient, openstackService: [OpenStackService](../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.html))<br>This controller redirects from the base url to the swagger ui |
| [SimpleCompute](-simple-compute/index.html) | [jvm]<br>@RestController<br>class [SimpleCompute](-simple-compute/index.html)(client: UCloudClient, openstackService: [OpenStackService](../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.html), wsDispatcher: UCloudWsDispatcher, provider: [ProviderProperties](../dk.aau.claaudia.openstackgateway.config/-provider-properties/index.html)) : JobsController |
| [TempController](-temp-controller/index.html) | [jvm]<br>@RestController<br>@RequestMapping(value = ["/ucloud/claaudia/compute/jobs/temp"])<br>class [TempController](-temp-controller/index.html)(openstackService: [OpenStackService](../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.html), templateService: [TemplateService](../dk.aau.claaudia.openstackgateway.services/-template-service/index.html))<br>Temporary endpoints for doing manual creation and deletion of stacks |

