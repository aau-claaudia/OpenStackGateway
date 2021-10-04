package dk.aau.claaudia.openstackgateway

import dk.aau.claaudia.openstackgateway.services.OpenStackService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class OpenStackServiceTest(@Autowired val openStackService: OpenStackService) {
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

    @AfterAll
    fun teardown() {
        println(">> Tear down")
    }
}
