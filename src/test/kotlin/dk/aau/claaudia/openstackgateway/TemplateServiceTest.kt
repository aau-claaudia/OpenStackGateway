package dk.aau.claaudia.openstackgateway

import dk.aau.claaudia.openstackgateway.services.TemplateService;
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;


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
                "public_keys" to mapOf(
                    "type" to "comma_delimited_list"
                ),
                "public_network" to mapOf(
                    "type" to "string",
                    "default" to "Campus Network 01"
                ),
                "flavor" to mapOf(
                    "type" to "string",
                    "default" to "ucloud_taste"
                ),
                "image" to mapOf(
                    "type" to "string",
                    "default" to "Cirros"
                ),
                "username" to mapOf(
                    "type" to "string",
                    "default" to "ucloud"
                ),
            )

            assertThat(templateParameters).isEqualTo(expectedParameters)

        }

        @AfterAll
        fun teardown() {
            println(">> Tear down")
        }





}