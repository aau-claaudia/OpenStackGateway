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
    fun `given name and version find openstack name`() {
        val image = openStackService.mapImage("aau-ubuntu-vm", "16.04")

        assertThat(image).isEqualTo("Ubuntu 16.04 LTS")
    }

    @Test
    fun `given name and version find blank`() {
        val image = openStackService.mapImage("aau-ubuntu-vm", "14.04")

        assertThat(image).isEqualTo("")
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
    fun `given job to delete send correct message`() {
        val job: Job = Job(
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

        val stackJson =
            """
            {
            "id": "3095aefc-09fb-4bc7-b1f0-f21a304e864c",
            "stack_name": "ucloud-1234",
            "stack_status": ${StackStatus.DELETE_IN_PROGRESS},
            }
            """

        val stack: Stack = mapper.readValue(stackJson, HeatStack::class.java)

        val deletedStackJson =
            """
            {
            "id": "3095aefc-09fb-4bc7-b1f0-f21a304e864c",
            "stack_name": "ucloud-1234",
            "stack_status": ${StackStatus.DELETE_COMPLETE},
            }
            """

        val deletedStack: Stack = mapper.readValue(deletedStackJson, HeatStack::class.java)

        val spyStack = spyk(openStackService)

        every { spyStack.findStackIncludeDeleted(job) } returnsMany listOf(stack, stack, stack, deletedStack)
        every { spyStack.sendJobStatusMessage(job.id, JobState.SUCCESS, "Stack DELETE complete") } returns Unit

        spyStack.monitorStackDeletion(job)

        verify { spyStack.sendJobStatusMessage(job.id, JobState.SUCCESS, "Stack DELETE complete") }
    }

    @AfterAll
    fun teardown() {
        println(">> Tear down")
    }
}
