package dk.aau.claaudia.openstackgateway.controllers


import dk.aau.claaudia.openstackgateway.config.ProviderProperties
import dk.aau.claaudia.openstackgateway.config.UCloudProperties
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

/**
 * This class implements Uclouds JobController from the provider integration package
 */
@RestController
@SecurityRequirement(name = "bearer-key") // Info for openapi interface
class SimpleCompute(
    private val client: UCloudClient,
    private val openstackService: OpenStackService,
    wsDispatcher: UCloudWsDispatcher,
    private val providerProperties: ProviderProperties,
    private val uCloudProperties: UCloudProperties,
) : JobsController(providerProperties.id, wsDispatcher) {
    init {
        log.info("Simple compute init")
    }

    /**
     * Create stacks in openstack from ucloud job.
     * Start each stack creation in openstack asynchronously with createStacks.
     * Then start a task that monitors the stacks in openstack.
     * When they start or report an error this message and status should be sent to ucloud.
     */
    override fun create(request: BulkRequest<Job>) {
        log.info("Creating stacks: $request")
        openstackService.createStacks(request.items)

        log.info("Waiting for stacks to start: $request")
        openstackService.sendStatusWhenStackComplete(request.items)
    }

    /**
     * Delete specific stacks in openstack.
     * It is important to make sure the ucloud jobs are charged before beeing deleted.
     * The function chargeDeleteJobs will first send a charge request to ucloud
     * and the start the deletion process.
     * When an async task has been started for each job,
     * then start a task that monitors the stacks in openstack.
     * When they can no longer be found we assume deletion and status should be sent to ucloud.
     */
    override fun delete(request: BulkRequest<Job>) {
        log.info("Charging and deleting jobs: $request")
        openstackService.chargeDeleteJobs(request.items)

        log.info("Waiting for stacks to be deleted: $request")
        openstackService.monitorStackDeletions(request.items)
    }

    /**
     * This currently has no use for Virtual Machines and does nothing.
     */
    override fun extend(request: BulkRequest<JobsProviderExtendRequestItem>) {
        log.info("Extending some jobs: $request")
        // TODO What does extend mean for a VM?
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

    /**
     * Provide ucloud with the available products.
     * These are basically equivalent to openstack flavors but need to adhere to the ucloud format
     * This includes information additional information, e.g., product is Virtual Machine
     * For now there is only a single product category but that can be expanded and maybe saved as tags on flavors
     * All information about ComputeSupport is also hardcoded for now
     */
    override fun retrieveProducts(request: Unit): JobsProviderRetrieveProductsResponse {
        log.info("Retrieving products")

        val response = JobsProviderRetrieveProductsResponse(
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

        logger().info(response.toString())
        return response
    }

    /**
     * Unused for now
     */
    override fun retrieveUtilization(request: Unit): JobsProviderUtilizationResponse {
        log.info("Retrieving utilization")
        return JobsProviderUtilizationResponse(CpuAndMemory(0.0, 0L), CpuAndMemory(0.0, 0L), QueueStatus(0, 0))
    }

    /**
     * Unused for now.
     * Ucloud plans to implement this and we need to decide how to suspend stacks in openstack
     */
    override fun suspend(request: BulkRequest<Job>) {
        log.info("suspend jobs: $request")
    }

    /**
     * Verify that ucloud has the corrent status of the provided jobs/stacks
     */
    override fun verify(request: BulkRequest<Job>) {
        log.info("verify jobs: $request")
        openstackService.verifyJobs(request.items)
    }

    companion object : Loggable {
        override val log = logger()
    }
}
