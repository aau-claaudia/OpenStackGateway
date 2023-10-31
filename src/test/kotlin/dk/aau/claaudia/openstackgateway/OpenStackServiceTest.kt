package dk.aau.claaudia.openstackgateway;

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import dk.aau.claaudia.openstackgateway.config.Messages
import dk.aau.claaudia.openstackgateway.config.OpenStackProperties
import dk.aau.claaudia.openstackgateway.config.ProviderProperties
import dk.aau.claaudia.openstackgateway.extensions.ExtensionConfig
import dk.aau.claaudia.openstackgateway.extensions.ucloudId
import dk.aau.claaudia.openstackgateway.models.StackStatus
import dk.aau.claaudia.openstackgateway.services.OpenStackService
import dk.aau.claaudia.openstackgateway.services.TemplateService
import dk.sdu.cloud.FindByStringId
import dk.sdu.cloud.accounting.api.Product
import dk.sdu.cloud.accounting.api.ProductCategoryId
import dk.sdu.cloud.accounting.api.ProductPriceUnit
import dk.sdu.cloud.accounting.api.providers.ResourceChargeCreditsResponse
import dk.sdu.cloud.app.orchestrator.api.*
import dk.sdu.cloud.app.store.api.NameAndVersion
import dk.sdu.cloud.provider.api.ResourceOwner
import dk.sdu.cloud.providers.UCloudClient
import io.ktor.util.date.*
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.openstack4j.model.compute.Flavor
import org.openstack4j.model.compute.Server
import org.openstack4j.model.heat.Stack
import org.openstack4j.openstack.compute.domain.NovaFlavor
import org.openstack4j.openstack.compute.domain.NovaServer
import org.openstack4j.openstack.heat.domain.HeatStack
import org.openstack4j.openstack.image.v2.domain.GlanceImage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

@ExtendWith(SpringExtension::class)
@Import(
    value = [
        OpenStackService::class,
        TemplateService::class,
        UCloudClient::class,
        ExtensionConfig::class]
)
@ContextConfiguration(initializers = [ConfigDataApplicationContextInitializer::class])
@EnableConfigurationProperties(value = [OpenStackProperties::class, ProviderProperties::class, Messages::class])
class OpenStackServiceTest(
    @Autowired
    private var messages: Messages
) {

    @MockkBean
    private lateinit var templateService: TemplateService

    @MockkBean
    private lateinit var uCloudClient: UCloudClient

    @SpykBean
    private lateinit var openStackService: OpenStackService

    private var mapper: ObjectMapper = ObjectMapper()

    private fun getTestJob(jobState: JobState = JobState.IN_QUEUE): Job {
        return Job(
            "44",
            ResourceOwner("testmayn", "projekt1"),
            emptyList(),
            JobSpecification(
                NameAndVersion("name", "version"),
                ComputeProductReference("product1", "category1", "provider1"),
                "navnet",
                1,
                false,
                null,
                null,
                null,
                null
            ),
            JobStatus(
                jobState,
                null,
                null,
                null,
                null,
                null,
                Product.Compute(
                    description = "Some product",
                    name = "Product 42",
                    category = ProductCategoryId("standard", "aau-test"),
                    pricePerUnit = 1,
                    unitOfPrice = ProductPriceUnit.CREDITS_PER_MINUTE,
                    cpu = 4,
                )
            ),
            getTimeMillis(),
            null,
            null
        )
    }

    fun getTestServerActive(): Server {
        val serverJson =
            """
            {
                "id": "4d3cca62-3150-4efd-a3b9-b6f5a4c28323",
                "status": "${Server.Status.ACTIVE}"
            }
            """

        return mapper.readValue(serverJson, NovaServer::class.java)
    }

    fun getTestServerShutoff(): Server {
        val serverJson =
            """
            {
                "id": "4d3cca62-3150-4efd-a3b9-b6f5a4c28323",
                "status": "${Server.Status.SHUTOFF}"
            }
            """

        return mapper.readValue(serverJson, NovaServer::class.java)
    }

    fun getStackCreateInProgress(): HeatStack {
        val stackJson =
            """
            {
                "id": "3095aefc-09fb-4bc7-b1f0-f21a304e864c",
                "stack_name": "ucloud-1234",
                "stack_status": "${StackStatus.CREATE_IN_PROGRESS}"
            }
            """

        return mapper.readValue(stackJson, HeatStack::class.java)
    }

    fun getStackCreateComplete(): HeatStack {
        val stackJson =
            """
            {
                "id": "3095aefc-09fb-4bc7-b1f0-f21a304e864c",
                "stack_name": "ucloud-1234",
                "stack_status": "${StackStatus.CREATE_COMPLETE}",
                "outputs": [
                    {
                        "output_key": "server_ip",
                        "output_value": "127.0.0.1"
                    }
                ]
            }
            """

        return mapper.readValue(stackJson, HeatStack::class.java)
    }

    fun getStackUpdateComplete(): HeatStack {
        val stackJson =
            """
            {
                "id": "3095aefc-09fb-4bc7-b1f0-f21a304e864c",
                "stack_name": "ucloud-1234",
                "stack_status": "${StackStatus.UPDATE_COMPLETE}",
                "creation_time": "2021-10-04T20:59:46Z",
                "tags": ["lastcharged:2021-10-05T20:59:46Z"],
                "outputs": [
                    {
                        "output_key": "server_ip",
                        "output_value": "127.0.0.1"
                    }
                ]
            }
            """

        return mapper.readValue(stackJson, HeatStack::class.java)
    }

    fun getStackUpdateCompleteWithLastCharged(lastCharged: Instant): HeatStack {
        val stackJson =
            """
            {
                "id": "3095aefc-09fb-4bc7-b1f0-f21a304e864c",
                "stack_name": "ucloud-1234",
                "stack_status": "${StackStatus.UPDATE_COMPLETE}",
                "creation_time": "2021-10-04T20:59:46Z",
                "tags": ["lastcharged:${lastCharged}"],
                "outputs": [
                    {
                        "output_key": "server_ip",
                        "output_value": "127.0.0.1"
                    }
                ]
            }
            """

        return mapper.readValue(stackJson, HeatStack::class.java)
    }

    fun getStackCreateFailed(): HeatStack {
        val stackJson =
            """
            {
                "id": "3095aefc-09fb-4bc7-b1f0-f21a304e864c",
                "stack_name": "ucloud-1234",
                "stack_status": "${StackStatus.CREATE_FAILED}"
            }
            """

        return mapper.readValue(stackJson, HeatStack::class.java)
    }

    fun getStackDeleteInProgress(): HeatStack {
        val stackJson =
            """
            {
            "id": "3095aefc-09fb-4bc7-b1f0-f21a304e864c",
            "stack_name": "ucloud-1234",
            "stack_status": "${StackStatus.DELETE_IN_PROGRESS}"
            }
            """

        return mapper.readValue(stackJson, HeatStack::class.java)
    }

    fun getStackDeleteComplete(): HeatStack {
        val stackJson =
            """
            {
            "id": "3095aefc-09fb-4bc7-b1f0-f21a304e864c",
            "stack_name": "ucloud-1234",
            "stack_status": "${StackStatus.DELETE_COMPLETE}"
            }
            """

        return mapper.readValue(stackJson, HeatStack::class.java)
    }

    fun getStackDeleteFailed(): HeatStack {
        val stackJson =
            """
            {
            "id": "3095aefc-09fb-4bc7-b1f0-f21a304e864c",
            "stack_name": "ucloud-1234",
            "stack_status": "${StackStatus.DELETE_FAILED}"
            }
            """

        return mapper.readValue(stackJson, HeatStack::class.java)
    }

    fun getStackCheckInProgress(): HeatStack {
        val stackJson =
            """
            {
            "id": "3095aefc-09fb-4bc7-b1f0-f21a304e864c",
            "stack_name": "ucloud-1234",
            "stack_status": "${StackStatus.CHECK_IN_PROGRESS}"
            }
            """

        return mapper.readValue(stackJson, HeatStack::class.java)
    }

    @Test
    fun `given name and version find openstack id`() {
        val image = openStackService.mapImage("aau-ubuntu-vm", "20.04", "very-strange-name")

        assertThat(image).isEqualTo("f94ae063-dba2-42be-8435-6f031583fe1f")
    }

    @Test
    fun `given another name and version find openstack id`() {
        val image = openStackService.mapImage("aau-ubuntu-vm", "18.04", "is-not-used-here")

        assertThat(image).isEqualTo("d94ae063-dba2-42be-8435-6f031583fe1f")
    }

    @Test
    fun `given new naming convention name and version map to correct openstack name`() {
        val imageJson =
            """
            {
            "name": "ucloud-cuda-ubuntu-20.04",
            "id": "1bea47ed-f6a9-463b-b423-14b9cca9ad27"
            }
            """

        val openstackImage = mapper.readValue(imageJson, GlanceImage::class.java)

        every { openStackService.listImages(any()) } returns listOf(
            openstackImage,
        )

        val image = openStackService.mapImage("cuda-ubuntu", "20.04", "ucloud-cuda-ubuntu-20.04")

        assertThat(image).isEqualTo("1bea47ed-f6a9-463b-b423-14b9cca9ad27")
    }

    @Test
    fun `given name and version find openstack name`() {
        val image = openStackService.mapImage("aau-ubuntu-vm", "16.04", "not used")

        assertThat(image).isEqualTo("Ubuntu 16.04 LTS")
    }

    @Test
    fun `given stack without lastcharged return stack creation time`() {
        val stackJson =
            """
            {
            "id": "3095aefc-09fb-4bc7-b1f0-f21a304e864c",
            "stack_name": "ucloud-1234",
            "creation_time": "2021-10-05T20:59:46Z"
            }
            """

        val stack: Stack = mapper.readValue(stackJson, HeatStack::class.java)
        val lastCharged = openStackService.getLastChargedFromStack(stack)

        assertThat(lastCharged).isEqualTo(Instant.parse("2021-10-05T20:59:46Z"))
    }

    @Test
    fun `given stack with lastcharged return lastcharged`() {
        val stackJson =
            """
            {
            "id": "3095aefc-09fb-4bc7-b1f0-f21a304e864c",
            "stack_name": "ucloud-1234",
            "creation_time": "2021-10-04T20:59:46Z",
            "tags": ["lastcharged:2021-10-05T20:59:46Z"]
            }
            """

        val stack: Stack = mapper.readValue(stackJson, HeatStack::class.java)
        val lastCharged = openStackService.getLastChargedFromStack(stack)

        assertThat(lastCharged).isEqualTo(Instant.parse("2021-10-05T20:59:46Z"))
    }

    @Test
    fun `monitor stacks deletion send correct success message`() {
        val stackDeleteInProgress = getStackDeleteInProgress()
        val stackDeleteComplete = getStackDeleteComplete()

        val job: Job = getTestJob()

        every { openStackService.findStackIncludeDeleted(job) } returnsMany listOf(
            stackDeleteInProgress,
            stackDeleteInProgress,
            stackDeleteInProgress,
            stackDeleteComplete
        )
        every { openStackService.sendJobStatusMessage(job.id, JobState.SUCCESS, "Stack DELETE complete") } returns Unit

        openStackService.monitorDeletion(job)

        verify { openStackService.sendJobStatusMessage(job.id, JobState.SUCCESS, "Stack DELETE complete") }
    }

    @Test
    fun `monitor failed stacks deletion reach timeout send nothing`() {
        val stackDeleteInProgress = getStackDeleteInProgress()
        val stackDeleteFailed = getStackDeleteFailed()

        val job: Job = getTestJob()

        every { openStackService.findStackIncludeDeleted(job) } returnsMany listOf(
            stackDeleteInProgress,
            stackDeleteInProgress,
            stackDeleteInProgress,
            stackDeleteFailed
        )

        openStackService.monitorDeletion(job)

        verify(inverse = true) { openStackService.sendJobStatusMessage(job.id, any(), any()) }
    }

    @Test
    fun `monitor stack creation send correct running message`() {
        val stackCreateInProgress: Stack = getStackCreateInProgress()
        val stackCreateComplete: Stack = getStackCreateComplete()
        val job: Job = getTestJob()


        every { openStackService.getStackByJob(job) } returnsMany listOf(
            stackCreateInProgress,
            stackCreateInProgress,
            stackCreateInProgress,
            stackCreateComplete
        )
        every { openStackService.sendJobStatusMessage(job.id, JobState.RUNNING, any()) } returns Unit

        openStackService.monitorCreation(job)

        verify {
            openStackService.sendJobStatusMessage(
                job.id, JobState.RUNNING, any()
            )
        }
    }

    @Test
    fun `monitor failed stack creation send correct failed message`() {
        val stackCreateInProgress: Stack = getStackCreateInProgress()
        val stackCreateFailed: Stack = getStackCreateFailed()
        val job: Job = getTestJob()

        every { openStackService.getStackByJob(job) } returnsMany listOf(
            stackCreateInProgress,
            stackCreateInProgress,
            stackCreateInProgress,
            stackCreateFailed
        )
        every { openStackService.sendJobStatusMessage(job.id, JobState.FAILURE, any()) } returns Unit

        openStackService.monitorCreation(job)

        verify { openStackService.sendJobStatusMessage(job.id, JobState.FAILURE, messages.jobs.createFailed) }
    }

    @Test
    fun `charge job successful`() {
        val stack: Stack = getStackUpdateComplete()

        // Mocked job from ucloud
        val job: Job = getTestJob()
        every { openStackService.retrieveUcloudJob(stack.ucloudId) } returns job

        // Mocked instance response from openstack
        val instance: Server = getTestServerActive()
        every { openStackService.getInstanceFromStack(stack) } returns instance

        // Mocked ucloud charge response. Empty lists means charge was successful
        val chargeCreditsResponse =
            ResourceChargeCreditsResponse(duplicateCharges = listOf(), insufficientFunds = listOf())
        every { openStackService.chargeCreditsJob(any(), any(), any(), any()) } returns chargeCreditsResponse
        every { openStackService.checkCreditsJob(any(), any(), any(), any()) } returns chargeCreditsResponse

        // Expect charge ok and timestamp is updated
        every { openStackService.updateStackLastCharged(stack, any()) } returns true

        openStackService.chargeStack(stack)

        val chargeTime: Instant = Instant.now()
        val lastChargedTime: Instant = openStackService.getLastChargedFromStack(stack)
        val duration = Duration.between(lastChargedTime, chargeTime)

        assertThat(duration).isNotNull()

        //Verify charge call
        verify { openStackService.chargeCreditsJob(job.id, any(), 4, duration.toMinutes()) }

        //Verify a successful charge
        verify {
            openStackService.updateStackLastCharged(
                stack,
                lastChargedTime.plus(duration.toMinutes(), ChronoUnit.MINUTES)
            )
        }
    }

    @Test
    fun `charge job insufficient funds instance shutdown`() {
        val stack: Stack = getStackUpdateComplete()

        // Mocked job from ucloud
        val job: Job = getTestJob()
        every { openStackService.retrieveUcloudJob(stack.ucloudId) } returns job

        // Mocked instance response from openstack
        val instance: Server = getTestServerActive()
        every { openStackService.getInstanceFromStack(stack) } returns instance

        // Mocked ucloud charge response. Id is added in insufficient funds
        val chargeCreditsResponse = ResourceChargeCreditsResponse(
            duplicateCharges = listOf(),
            insufficientFunds = listOf(FindByStringId(job.id))
        )
        every { openStackService.chargeCreditsJob(any(), any(), any(), any()) } returns chargeCreditsResponse
        every { openStackService.checkCreditsJob(any(), any(), any(), any()) } returns chargeCreditsResponse

        //Mock shutdown methods
        every { openStackService.sendInstanceShutdownAction(any()) } returns Unit
        every { openStackService.asyncMonitorShutdownInstanceSendUpdate(any(), any()) } returns Unit

        openStackService.chargeStack(stack)

        //Thread.sleep(2000)
        // Verify shutdown and monitor is called
        verify(exactly = 1) { openStackService.sendInstanceShutdownAction(instance) }
        verify(exactly = 1) { openStackService.asyncMonitorShutdownInstanceSendUpdate(instance, job) }
        verify(exactly = 0) { openStackService.updateStackLastCharged(stack, any()) }
    }

    @Test
    fun `monitor instance stopping send correct message`() {
        val job: Job = getTestJob()
        val activeServer: Server = getTestServerActive()
        val shutoffServer: Server = getTestServerShutoff()

        every { openStackService.getInstanceFromId(activeServer.id) } returnsMany listOf(
            activeServer,
            activeServer,
            activeServer,
            shutoffServer
        )
        every { openStackService.sendJobStatusMessage(job.id, JobState.SUSPENDED, any()) } returns Unit

        openStackService.monitorShutdownInstanceSendUpdate(activeServer, job)

        verify { openStackService.sendJobStatusMessage(job.id, JobState.SUSPENDED, messages.jobs.instanceShutdown) }
    }

    @Test
    fun `charge job shutoff instance now has sufficient funds to charge`() {
        val stack: Stack = getStackUpdateComplete()

        // Mocked job from ucloud
        val job: Job = getTestJob()
        every { openStackService.retrieveUcloudJob(stack.ucloudId) } returns job

        // Mocked instance response from openstack
        val instance: Server = getTestServerShutoff()
        every { openStackService.getInstanceFromStack(stack) } returns instance

        // Mocked ucloud charge response. Id is added in insufficient funds
        val chargeCreditsResponse = ResourceChargeCreditsResponse(
            duplicateCharges = listOf(),
            insufficientFunds = listOf()
        )
        every { openStackService.chargeCreditsJob(any(), any(), any(), any()) } returns chargeCreditsResponse
        every { openStackService.checkCreditsJob(any(), any(), any(), any()) } returns chargeCreditsResponse

        //Mock shutdown/resume methods
        every { openStackService.sendInstanceShutdownAction(any()) } returns Unit
        every { openStackService.asyncMonitorShutdownInstanceSendUpdate(any(), any()) } returns Unit
        every { openStackService.sendInstanceStartAction(any()) } returns Unit
        every { openStackService.asyncMonitorStartInstanceSendUpdate(any(), any()) } returns Unit

        // Expect charge ok and timestamp is updated
        every { openStackService.updateStackLastCharged(stack, any()) } returns true

        openStackService.chargeStack(stack)

        // Verify start and monitor is called
        verify(exactly = 1) { openStackService.sendInstanceStartAction(instance) }
        verify(exactly = 1) { openStackService.asyncMonitorStartInstanceSendUpdate(instance, job) }
        verify(exactly = 1) { openStackService.updateStackLastCharged(stack, any()) }
        verify(exactly = 0) { openStackService.sendInstanceShutdownAction(instance) }
        verify(exactly = 0) { openStackService.asyncMonitorShutdownInstanceSendUpdate(instance, job) }
    }

    @Test
    fun `monitor instance starting send correct message`() {
        val job: Job = getTestJob()
        val activeServer: Server = getTestServerActive()
        val shutoffServer: Server = getTestServerShutoff()

        every { openStackService.getInstanceFromId(activeServer.id) } returnsMany listOf(
            shutoffServer,
            shutoffServer,
            shutoffServer,
            shutoffServer,
            activeServer,
        )
        every { openStackService.sendJobStatusMessage(job.id, JobState.RUNNING, any()) } returns Unit

        openStackService.monitorStartInstanceSendUpdate(shutoffServer, job)

        verify(exactly = 1) {
            openStackService.sendJobStatusMessage(
                job.id,
                JobState.RUNNING,
                messages.jobs.instanceRestarted
            )
        }
    }

    @Test
    fun `given shutoff instance below cleanup threshold verify no deletion`() {
        val stack = getStackUpdateCompleteWithLastCharged(Instant.now().minus(5, ChronoUnit.DAYS))

        every { openStackService.getActiveStacks() } returns listOf(stack)
        every { openStackService.getInstanceFromStack(any()) } returns getTestServerShutoff()
        every { openStackService.retrieveUcloudJob(any()) } returns getTestJob()

        every { openStackService.deleteJob(any()) } returns Unit
        every { openStackService.asyncMonitorDeletions(any()) } returns Unit

        openStackService.deleteNotChargedStacks()

        verify(exactly = 0) { openStackService.deleteJob(any()) }
        verify(exactly = 0) { openStackService.asyncMonitorDeletions(any()) }
    }

    @Test
    fun `given shutoff instance above cleanup threshold verify deletion`() {
        val stack = getStackUpdateCompleteWithLastCharged(Instant.now().minus(32, ChronoUnit.DAYS))

        every { openStackService.getActiveStacks() } returns listOf(stack)
        every { openStackService.getInstanceFromStack(any()) } returns getTestServerShutoff()
        every { openStackService.retrieveUcloudJob(any()) } returns getTestJob()

        every { openStackService.deleteJob(any()) } returns Unit
        every { openStackService.asyncMonitorDeletions(any()) } returns Unit

        openStackService.deleteNotChargedStacks()

        verify(exactly = 1) { openStackService.deleteJob(any()) }
        verify(exactly = 1) { openStackService.asyncMonitorDeletions(any()) }
    }

    @Test
    fun `given in-queue job and create in progress stack verify will not send update`() {
        val stack: Stack = getStackCreateInProgress()

        val job: Job = getTestJob()
        every { openStackService.retrieveUcloudJob(stack.ucloudId) } returns job
        every { openStackService.getStackByJob(job, any()) } returns stack
        every { openStackService.getInstanceFromStack(any()) } returns null
        every { openStackService.sendJobStatusMessage(any(), any(), any()) } returns Unit

        openStackService.verifyJob(job)

        verify(exactly = 0) { openStackService.sendJobStatusMessage(any(), any(), any()) }
    }

    @Test
    fun `given in-queue job and no stack stack verify will not send update`() {
        val stack: Stack = getStackCreateInProgress()
        val job: Job = getTestJob()
        every { openStackService.retrieveUcloudJob(stack.ucloudId) } returns job
        every { openStackService.getStackByJob(job, any()) } returns null

        openStackService.verifyJob(job)

        verify(exactly = 0) { openStackService.sendJobStatusMessage(any(), any(), any()) }
    }

    @Test
    fun `given in-queue job and create complete stack verify will send running update`() {
        val stack: Stack = getStackCreateComplete()

        val job: Job = getTestJob()
        every { openStackService.retrieveUcloudJob(stack.ucloudId) } returns job
        every { openStackService.getStackByJob(job, any()) } returns stack
        every { openStackService.getInstanceFromStack(any()) } returns null
        every { openStackService.sendJobStatusMessage(any(), any(), any()) } returns Unit

        openStackService.verifyJob(job)

        verify(exactly = 1) { openStackService.sendJobStatusMessage(any(), JobState.RUNNING, any()) }
    }

    @Test
    fun `given in-queue job and create complete stack verify will send failed update`() {
        val stack: Stack = getStackCreateFailed()

        val job: Job = getTestJob()
        every { openStackService.retrieveUcloudJob(stack.ucloudId) } returns job
        every { openStackService.getStackByJob(job, any()) } returns stack
        every { openStackService.getInstanceFromStack(any()) } returns null
        every { openStackService.sendJobStatusMessage(any(), any(), any()) } returns Unit

        openStackService.verifyJob(job)

        verify(exactly = 1) { openStackService.sendJobStatusMessage(any(), JobState.FAILURE, any()) }
    }

    @Test
    fun `given running job and update complete stack with shutoff instance verify will send suspended update`() {
        val stack: Stack = getStackUpdateComplete()

        val job: Job = getTestJob(JobState.RUNNING)
        every { openStackService.retrieveUcloudJob(stack.ucloudId) } returns job
        every { openStackService.getStackByJob(job, any()) } returns stack
        every { openStackService.getInstanceFromStack(any()) } returns getTestServerShutoff()
        every { openStackService.sendJobStatusMessage(any(), any(), any()) } returns Unit

        openStackService.verifyJob(job)

        verify(exactly = 1) { openStackService.sendJobStatusMessage(any(), JobState.SUSPENDED, any()) }
    }

    @Test
    fun `given running job and CHECK_IN_PROGRESS stack verify will send no update`() {
        // This status is unknown to verify function
        val stack: Stack = getStackCheckInProgress()

        val job: Job = getTestJob(JobState.RUNNING)
        every { openStackService.retrieveUcloudJob(stack.ucloudId) } returns job
        every { openStackService.getStackByJob(job, any()) } returns stack
        every { openStackService.getInstanceFromStack(any()) } returns null
        every { openStackService.sendJobStatusMessage(any(), any(), any()) } returns Unit

        openStackService.verifyJob(job)

        verify(exactly = 0) { openStackService.sendJobStatusMessage(any(), any(), any()) }
    }

    @Test
    fun `test retrieve products`() {
        val flavor1: Flavor = mapper.readValue(
            """
            {
            "id": "3095aefc-09fb-4bc7-b1f0-f21a304e864c",
            "name": "uc-t4"
            }
            """, NovaFlavor::class.java
        )
        val flavor2: Flavor = mapper.readValue(
            """
            {
            "id": "3095aefc-09fb-4bc7-b1f0-f21a304e864l",
            "name": "uc-t4-h"
            }
            """, NovaFlavor::class.java
        )
        val flavor3: Flavor = mapper.readValue(
            """
            {
            "id": "3095aefc-09fb-4bc7-b1f0-f21a304e864v",
            "name": "uc-general-small"
            }
            """, NovaFlavor::class.java
        )
        val flavor4: Flavor = mapper.readValue(
            """
            {
            "id": "3095aefc-09fb-4bc7-b1f0-f21a304e864b",
            "name": "uc-general-small-h"
            }
            """, NovaFlavor::class.java
        )

        every { openStackService.listFlavors() } returns listOf(
            flavor1, flavor2, flavor3, flavor4
        )

        every { openStackService.getFlavorExtraSpecs("3095aefc-09fb-4bc7-b1f0-f21a304e864c") } returns mutableMapOf(
            "availability_zone" to "uc-t4"
        )
        every { openStackService.getFlavorExtraSpecs("3095aefc-09fb-4bc7-b1f0-f21a304e864l") } returns mutableMapOf(
            "availability_zone" to "uc-t4"
        )
        every { openStackService.getFlavorExtraSpecs("3095aefc-09fb-4bc7-b1f0-f21a304e864v") } returns mutableMapOf(
            "availability_zone" to "uc-general"
        )
        every { openStackService.getFlavorExtraSpecs("3095aefc-09fb-4bc7-b1f0-f21a304e864b") } returns mutableMapOf(
            "availability_zone" to "uc-general"
        )

        val retrievedProducts = openStackService.retrieveProducts()

        assertThat(retrievedProducts.get(0).product.id).isEqualTo("uc-t4")
        assertThat(retrievedProducts.get(0).product.category).isEqualTo("uc-t4")

        assertThat(retrievedProducts.get(1).product.id).isEqualTo("uc-t4-h")
        assertThat(retrievedProducts.get(1).product.category).isEqualTo("uc-t4-h")

        assertThat(retrievedProducts.get(2).product.id).isEqualTo("uc-general-small")
        assertThat(retrievedProducts.get(2).product.category).isEqualTo("uc-general")

        assertThat(retrievedProducts.get(3).product.id).isEqualTo("uc-general-small-h")
        assertThat(retrievedProducts.get(3).product.category).isEqualTo("uc-general-h")
    }
}