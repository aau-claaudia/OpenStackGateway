//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.controllers](../index.md)/[TempController](index.md)

# TempController

[jvm]\
@RestController

@RequestMapping(value = ["/ucloud/claaudia/compute/jobs/temp"])

class [TempController](index.md)(openstackService: [OpenStackService](../../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.md), templateService: [TemplateService](../../dk.aau.claaudia.openstackgateway.services/-template-service/index.md))

Temporary endpoints for doing manual creation and deletion of stacks

## Constructors

| | |
|---|---|
| [TempController](-temp-controller.md) | [jvm]<br>fun [TempController](-temp-controller.md)(openstackService: [OpenStackService](../../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.md), templateService: [TemplateService](../../dk.aau.claaudia.openstackgateway.services/-template-service/index.md)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [jvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [createJobs](create-jobs.md) | [jvm]<br>@PostMapping(value = ["/"])<br>@ResponseStatus(value = HttpStatus.OK)<br>fun [createJobs](create-jobs.md)(@RequestBodyrequest: [TempJobRequest](../../dk.aau.claaudia.openstackgateway.models.requests/-temp-job-request/index.md)) |
| [getStack](get-stack.md) | [jvm]<br>@GetMapping(value = ["/{id}"])<br>fun [getStack](get-stack.md)(@PathVariableid: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Stack? |
