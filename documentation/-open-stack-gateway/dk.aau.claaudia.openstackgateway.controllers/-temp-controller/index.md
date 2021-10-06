---
title: TempController
---
//[OpenStackGateway](../../../index.html)/[dk.aau.claaudia.openstackgateway.controllers](../index.html)/[TempController](index.html)



# TempController



[jvm]\
@RestController



@RequestMapping(value = ["/ucloud/claaudia/compute/jobs/temp"])



class [TempController](index.html)(openstackService: [OpenStackService](../../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.html), templateService: [TemplateService](../../dk.aau.claaudia.openstackgateway.services/-template-service/index.html))

Temporary endpoints for doing manual creation and deletion of stacks



## Constructors


| | |
|---|---|
| [TempController](-temp-controller.html) | [jvm]<br>fun [TempController](-temp-controller.html)(openstackService: [OpenStackService](../../dk.aau.claaudia.openstackgateway.services/-open-stack-service/index.html), templateService: [TemplateService](../../dk.aau.claaudia.openstackgateway.services/-template-service/index.html)) |


## Types


| Name | Summary |
|---|---|
| [Companion](-companion/index.html) | [jvm]<br>object [Companion](-companion/index.html) |


## Functions


| Name | Summary |
|---|---|
| [createJobs](create-jobs.html) | [jvm]<br>@PostMapping(value = ["/"])<br>@ResponseStatus(value = HttpStatus.OK)<br>fun [createJobs](create-jobs.html)(@RequestBodyrequest: [TempJobRequest](../../dk.aau.claaudia.openstackgateway.models.requests/-temp-job-request/index.html)) |
| [getStack](get-stack.html) | [jvm]<br>@GetMapping(value = ["/{id}"])<br>fun [getStack](get-stack.html)(@PathVariableid: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): Stack? |

