//[OpenStackGateway](../../index.md)/[dk.aau.claaudia.openstackgateway.controllers](index.md)

# Package dk.aau.claaudia.openstackgateway.controllers

## Types

| Name | Summary |
|---|---|
| [HomeController](-home-controller/index.md) | [jvm]<br>@RestController<br>class [HomeController](-home-controller/index.md)(client: UCloudClient, openstackService: [OpenStackService](../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.md))<br>This controller redirects from the base url to the swagger ui |
| [SimpleCompute](-simple-compute/index.md) | [jvm]<br>@RestController<br>class [SimpleCompute](-simple-compute/index.md)(client: UCloudClient, openstackService: [OpenStackService](../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.md), wsDispatcher: UCloudWsDispatcher, provider: [ProviderProperties](../dk.aau.claaudia.openstackgateway.config/-provider-properties/index.md)) : JobsController |
| [TempController](-temp-controller/index.md) | [jvm]<br>@RestController<br>@RequestMapping(value = ["/ucloud/claaudia/compute/jobs/temp"])<br>class [TempController](-temp-controller/index.md)(openstackService: [OpenStackService](../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.md), templateService: [TemplateService](../dk.aau.claaudia.openstackgateway.services/-template-service/index.md))<br>Temporary endpoints for doing manual creation and deletion of stacks |
