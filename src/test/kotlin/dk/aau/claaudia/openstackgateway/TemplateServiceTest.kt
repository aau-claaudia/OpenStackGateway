package dk.aau.claaudia.openstackgateway

import dk.aau.claaudia.openstackgateway.services.TemplateService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class TemplateServiceTest(@Autowired val templateService: TemplateService) {

    @BeforeAll
    fun setup() {
        println(">> Setup")
    }

    @Test
    fun test() {
        val testTemplate = templateService.getTestTemplate()
        val templateParameters = templateService.extractParameters(testTemplate)

        val expectedParameters = mapOf(
            "pubKey" to mapOf(
                "type" to "comma_delimited_list"
            ),
            "flavor" to mapOf(
                "constraints" to listOf(
                    mapOf(
                        "custom_constraint" to "nova.flavor"
                    )
                ),
                "default" to "m1.small",
                "description" to "Flavor for the server to be created",
                "type" to "string"
            ),
            "image" to mapOf(
                "constraints" to listOf(
                    mapOf(
                        "custom_constraint" to "glance.image"
                    )
                ),
                "description" to "Image ID or image name to use for the server",
                "type" to "string"
            ),
            "key_name" to mapOf(
                "constraints" to listOf(
                    mapOf(
                        "custom_constraint" to "nova.keypair"
                    )
                ),
                "description" to "Name of an existing key pair to use for the server",
                "type" to "string"
            ),
            "network" to mapOf(
                "description" to "Network used by the server",
                "type" to "string"
            ),
            "security_group" to mapOf(
                "description" to "Network security_group",
                "type" to "string"
            ),
            "username" to mapOf(
                "type" to "string",
                "default" to "ucloud"
            ),
            "volume_size" to mapOf(
                "default" to 10,
                "description" to "Volume size of the system volume of the Virtual Server",
                "type" to "string"
            )
        )

        assertThat(templateParameters).isEqualTo(expectedParameters)

    }

    @AfterAll
    fun teardown() {
        println(">> Tear down")
    }
}