package dk.aau.claaudia.openstackgateway

import com.fasterxml.jackson.databind.ObjectMapper
import dk.aau.claaudia.openstackgateway.models.StackStatus
import dk.aau.claaudia.openstackgateway.services.OpenStackService
import dk.sdu.cloud.app.orchestrator.api.*
import dk.sdu.cloud.app.store.api.NameAndVersion
import dk.sdu.cloud.provider.api.ResourceOwner
import io.ktor.util.date.*
import io.mockk.every
import io.mockk.spyk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.openstack4j.model.heat.Stack
import org.openstack4j.openstack.heat.domain.HeatStack
import org.openstack4j.openstack.image.v2.domain.GlanceImage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import java.time.Instant

@SpringBootTest
class OpenStackServiceTest(
    @Autowired val openStackService: OpenStackService,
    @Qualifier("YAMLMapper")
    private val mapper: ObjectMapper
    ) {

    @BeforeAll
    fun setup() {
        println(">> Setup")
    }

    fun getTestJob(): Job {
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
                null, null, null, null
            ),
            JobStatus(JobState.IN_QUEUE, null, null, null, null, null, null),
            getTimeMillis(),
            null,
            null
        )
    }

    fun getStackCreateInProgress(): HeatStack {
        val stackJson =
            """
            {
                "id": "3095aefc-09fb-4bc7-b1f0-f21a304e864c",
                "stack_name": "ucloud-1234",
                "stack_status": ${StackStatus.CREATE_IN_PROGRESS},
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
                "stack_status": ${StackStatus.CREATE_COMPLETE},
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
                "stack_status": ${StackStatus.CREATE_FAILED}
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
            "stack_status": ${StackStatus.DELETE_IN_PROGRESS},
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
            "stack_status": ${StackStatus.DELETE_COMPLETE},
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
            "stack_status": ${StackStatus.DELETE_FAILED},
            }
            """

        return mapper.readValue(stackJson, HeatStack::class.java)
    }

    @Test
    fun `given name and version find openstack id`() {
        val image = openStackService.mapImage("aau-ubuntu-vm", "20.04")

        assertThat(image).isEqualTo("f94ae063-dba2-42be-8435-6f031583fe1f")
    }

    @Test
    fun `given another name and version find openstack id`() {
        val image = openStackService.mapImage("aau-ubuntu-vm", "18.04")

        assertThat(image).isEqualTo("d94ae063-dba2-42be-8435-6f031583fe1f")
    }

    @Test
    fun `given new naming convention name and version map to correct openstack name`() {
        val imageJson =
            """
            {
            "name": "ucloud-cuda-ubuntu-20.04",
            "id": "1bea47ed-f6a9-463b-b423-14b9cca9ad27",
            },
            """

        val openstackImage = mapper.readValue(imageJson, GlanceImage::class.java)

        val spyStack = spyk(openStackService)
        every { spyStack.listImages(any()) } returns listOf(
            openstackImage,
        )

        val image = spyStack.mapImage("cuda-ubuntu", "20.04")

        assertThat(image).isEqualTo("1bea47ed-f6a9-463b-b423-14b9cca9ad27")
    }

    @Test
    fun `given name and version find openstack name`() {
        val image = openStackService.mapImage("aau-ubuntu-vm", "16.04")

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

        val spyStack = spyk(openStackService)

        val job: Job = getTestJob()

        every { spyStack.findStackIncludeDeleted(job) } returnsMany listOf(
            stackDeleteInProgress,
            stackDeleteInProgress,
            stackDeleteInProgress,
            stackDeleteComplete
        )
        every { spyStack.sendJobStatusMessage(job.id, JobState.SUCCESS, "Stack DELETE complete") } returns Unit

        spyStack.monitorDeletion(job)

        verify { spyStack.sendJobStatusMessage(job.id, JobState.SUCCESS, "Stack DELETE complete") }
    }

    @Test
    fun `monitor failed stacks deletion reach timeout send nothing`() {
        val stackDeleteInProgress = getStackDeleteInProgress()
        val stackDeleteFailed = getStackDeleteFailed()

        val spyStack = spyk(openStackService)

        val job: Job = getTestJob()

        every { spyStack.findStackIncludeDeleted(job) } returnsMany listOf(
            stackDeleteInProgress,
            stackDeleteInProgress,
            stackDeleteInProgress,
            stackDeleteFailed
        )

        spyStack.monitorDeletion(job)

        verify(inverse = true) { spyStack.sendJobStatusMessage(job.id, any(), any()) }
    }

    @Test
    fun `monitor stack creation send correct running message`() {
        val stackCreateInProgress: Stack = getStackCreateInProgress()
        val stackCreateComplete: Stack = getStackCreateComplete()
        val job: Job = getTestJob()

        val spyStack = spyk(openStackService)

        every { spyStack.getStackByJob(job) } returnsMany listOf(
            stackCreateInProgress,
            stackCreateInProgress,
            stackCreateInProgress,
            stackCreateComplete
        )
        every { spyStack.sendJobStatusMessage(job.id, JobState.RUNNING, any()) } returns Unit

        spyStack.monitorCreation(job)

        verify {
            spyStack.sendJobStatusMessage(
                job.id, JobState.RUNNING, any()
            )
        }
    }

    @Test
    fun `monitor failed stack creation send correct failed message`() {
        val stackCreateInProgress: Stack = getStackCreateInProgress()
        val stackCreateFailed: Stack = getStackCreateFailed()
        val job: Job = getTestJob()

        val spyStack = spyk(openStackService)

        every { spyStack.getStackByJob(job) } returnsMany listOf(
            stackCreateInProgress,
            stackCreateInProgress,
            stackCreateInProgress,
            stackCreateFailed
        )
        every { spyStack.sendJobStatusMessage(job.id, JobState.FAILURE, any()) } returns Unit

        spyStack.monitorCreation(job)

        verify { spyStack.sendJobStatusMessage(job.id, JobState.FAILURE, "TEST TEST Could not start instance") }
    }

    @AfterAll
    fun teardown() {
        println(">> Tear down")
    }
}
