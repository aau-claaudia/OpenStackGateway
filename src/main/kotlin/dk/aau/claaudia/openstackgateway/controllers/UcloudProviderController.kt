package dk.aau.claaudia.openstackgateway.controllers


import dk.aau.claaudia.openstackgateway.services.OpenStackService
import dk.sdu.cloud.CommonErrorMessage
import dk.sdu.cloud.accounting.api.ProductReference
import dk.sdu.cloud.app.orchestrator.api.*
import dk.sdu.cloud.calls.BulkRequest
import dk.sdu.cloud.calls.bulkRequestOf
import dk.sdu.cloud.calls.client.orThrow
import dk.sdu.cloud.providers.*
import dk.sdu.cloud.service.Loggable
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.*

import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.collections.HashMap


@RestController
@SecurityRequirement(name = "bearer-key") // Info til openapi interface
class SimpleCompute(
    private val client: UCloudClient,
    private val openstackService: OpenStackService,
    wsDispatcher: UCloudWsDispatcher
) : JobsController(PROVIDER_ID, wsDispatcher) {
    init {
        log.info("Simple compute init")
    }

    override fun create(request: BulkRequest<Job>) {
        log.info("Creating some stacks: $request")
        openstackService.createStacks(request.items)

        log.info("Waiting for stacks to start: $request")
        openstackService.monitorStackCreations(request.items)
    }

    override fun delete(request: BulkRequest<Job>) {
        log.info("Deleting some jobs: $request")

        //openstackService.deleteStacks(request.items)
        //openstackService.monitorStackDeletions(request.items)

        JobsControl.update.call(
            bulkRequestOf(request.items.map { job ->
                JobsControlUpdateRequestItem(
                    job.id,
                    JobState.SUCCESS,
                    "We are no longer running!"
                )
            }),
            client
        ).orThrow()
    }

    override fun extend(request: BulkRequest<JobsProviderExtendRequestItem>) {
        log.info("Extending some jobs: $request")
        JobsControl.update.call(
            bulkRequestOf(request.items.map { requestItem ->
                JobsControlUpdateRequestItem(
                    requestItem.job.id,
                    status = "We have extended your requestItem with ${requestItem.requestedTime}"
                )
            }),
            client
        ).orThrow()
    }

    private val threadPool = Executors.newCachedThreadPool()
    private val tasks = HashMap<String, AtomicBoolean>()

    override fun follow(
        request: JobsProviderFollowRequest,
        wsContext: UCloudWsContext<JobsProviderFollowRequest, JobsProviderFollowResponse, CommonErrorMessage>,
    ) {
        when (request) {
            is JobsProviderFollowRequest.Init -> {
                val isRunning = AtomicBoolean(true)
                val streamId = UUID.randomUUID().toString()
                synchronized(tasks) {
                    tasks[streamId] = isRunning
                }

                threadPool.execute {
                    wsContext.sendMessage(JobsProviderFollowResponse(streamId, -1))

                    var counter = 0
                    while (isRunning.get() && wsContext.session.isOpen) {
                        wsContext.sendMessage(JobsProviderFollowResponse(streamId, 0, "Hello, World! $counter\n"))
                        counter++
                        Thread.sleep(1000)
                    }

                    wsContext.sendResponse(JobsProviderFollowResponse("", -1), 200)
                }
            }
            is JobsProviderFollowRequest.CancelStream -> {
                synchronized(tasks) {
                    tasks.remove(request.streamId)?.set(false)
                    wsContext.sendResponse(JobsProviderFollowResponse("", -1), 200)
                }
            }
        }
    }

    override fun openInteractiveSession(
        request: BulkRequest<JobsProviderOpenInteractiveSessionRequestItem>,
    ): JobsProviderOpenInteractiveSessionResponse {
        log.info("open interactive session $request")
        TODO()
    }

    private val knownProducts = listOf(
        //ProductReference("standard1", "standard", PROVIDER_ID),
        ProductReference("ucloud_taste", "standard", PROVIDER_ID),
    )

    override fun retrieveProducts(request: Unit): JobsProviderRetrieveProductsResponse {
        log.info("Retrieving products")
        return JobsProviderRetrieveProductsResponse(
            knownProducts.map { productRef ->
                ComputeProductSupport(
                    productRef,
                    ComputeSupport(
                        ComputeSupport.Docker(
                            enabled = false,
                        ),
                        ComputeSupport.VirtualMachine(
                            enabled = true,
                            logs = true,
                            vnc = false
                        )
                    )
                )
            }
        )
    }

    override fun retrieveUtilization(request: Unit): JobsProviderUtilizationResponse {
        log.info("Retrieving utilization")
        return JobsProviderUtilizationResponse(CpuAndMemory(0.0, 0L), CpuAndMemory(0.0, 0L), QueueStatus(0, 0))
    }

    override fun suspend(request: BulkRequest<Job>) {
        log.info("suspend jobs: $request")
    }

    override fun verify(request: BulkRequest<Job>) {
        log.info("verify jobs: $request")
    }

    companion object : Loggable {
        const val PROVIDER_ID = "aau3"
        override val log = logger()
    }
}
