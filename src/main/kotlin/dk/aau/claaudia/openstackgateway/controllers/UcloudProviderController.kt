package dk.aau.claaudia.openstackgateway.controllers


import dk.aau.claaudia.openstackgateway.config.ProviderProperties
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
    wsDispatcher: UCloudWsDispatcher,
    private val provider: ProviderProperties,
) : JobsController(provider.id, wsDispatcher) {
    init {
        log.info("Simple compute init")
    }

    override fun create(request: BulkRequest<Job>) {
        log.info("Creating stacks: $request")
        openstackService.createStacks(request.items)

        log.info("Waiting for stacks to start: $request")
        openstackService.monitorStackCreations(request.items)
    }

    override fun delete(request: BulkRequest<Job>) {
        log.info("Deleting jobs: $request")
        openstackService.deleteStacks(request.items)

        log.info("Waiting for stacks to be deleted: $request")
        openstackService.monitorStackDeletions(request.items)
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
        log.info("open interactive session", request)
        val sessions: MutableList<OpenSession> = mutableListOf()
        // Use map here
        for (item in request.items) {
            sessions.add(OpenSession.Web(item.job.id, 4, "a"))
        }
        return JobsProviderOpenInteractiveSessionResponse(sessions)
        //TODO()
    }

    override fun retrieveProducts(request: Unit): JobsProviderRetrieveProductsResponse {
        log.info("Retrieving products")

        return JobsProviderRetrieveProductsResponse(
            provider.products.map { product ->
                ComputeProductSupport(
                    ProductReference(product.id, product.category, provider.id),
                    ComputeSupport(
                        ComputeSupport.Docker(
                            enabled = product.support.docker.enabled,
                            web = product.support.docker.web,
                            vnc = product.support.docker.vnc,
                            logs = product.support.docker.logs,
                            terminal = product.support.docker.terminal,
                            peers = product.support.docker.peers,
                            timeExtension = product.support.docker.timeExtension,
                            utilization = product.support.docker.utilization
                        ),
                        ComputeSupport.VirtualMachine(
                            enabled = product.support.virtualMachine.enabled,
                            logs = product.support.virtualMachine.logs,
                            vnc = product.support.virtualMachine.vnc,
                            terminal = product.support.virtualMachine.terminal,
                            suspension = product.support.virtualMachine.suspension,
                            timeExtension = product.support.virtualMachine.timeExtension,
                            utilization = product.support.virtualMachine.utilization
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
        openstackService.verifyJobs(request.items)
    }

    companion object : Loggable {
        override val log = logger()
    }
}
