package dk.aau.claaudia.openstackgateway

import com.fasterxml.jackson.databind.ObjectMapper
import dk.aau.claaudia.openstackgateway.services.OpenStackService
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
    fun `given stack wit lastcharged return lastcharged`() {
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

    @AfterAll
    fun teardown() {
        println(">> Tear down")
    }
}
