package dk.aau.claaudia.openstackgateway

import com.fasterxml.jackson.databind.ObjectMapper
import dk.aau.claaudia.openstackgateway.config.OpenStackProperties
import dk.aau.claaudia.openstackgateway.extensions.ucloudId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.openstack4j.model.heat.Stack
import org.openstack4j.openstack.heat.domain.HeatStack
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ExtensionsTest(
    @Autowired val config: OpenStackProperties,
    @Qualifier("YAMLMapper")
    private val mapper: ObjectMapper
) {
    @BeforeAll
    fun setup() {
        println(">> Setup")
    }

    @Test
    fun `given stack can remove prefix from name to get ucloud id `() {
        val stackJson =
            """
            {
            "id": "3095aefc-09fb-4bc7-b1f0-f21a304e864c",
            "stack_name": "ucloud-1234",
            }
            """

        val stack: Stack = mapper.readValue(stackJson, HeatStack::class.java)

        assertThat(stack.ucloudId).isEqualTo("1234")
    }

//    @Test
//    fun `given job can add prefix to get openstack name`() {
//        val b = Job(
//            "3095aefc-09fb-4bc7-b1f0-f21a304e864c",
//            JobOwner("Testmayn", "Testproject"),
//            listOf(),
//            JobBilling(100, 1, 1),
//            JobSpecification(
//                NameAndVersion("ubuntu", "20"),
//                ComputeProductReference("ubuntu1", "standard", "aau")
//            ),
//            JobStatus(JobState.RUNNING),
//            99999
//        )
//
//        assertThat(b.openstackName).isEqualTo("ucloud-3095aefc-09fb-4bc7-b1f0-f21a304e864c")
//    }

    @AfterAll
    fun teardown() {
        println(">> Tear down")
    }
}
