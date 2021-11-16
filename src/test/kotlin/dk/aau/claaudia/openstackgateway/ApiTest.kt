package dk.aau.claaudia.openstackgateway

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ninjasquad.springmockk.MockkBean
import dk.aau.claaudia.openstackgateway.services.OpenStackService
import dk.aau.claaudia.openstackgateway.services.TemplateService
import dk.aau.claaudia.openstackgateway.tasks.Jobs
import dk.sdu.cloud.providers.UCloudClient
import io.mockk.every
import org.junit.jupiter.api.Test
import org.openstack4j.openstack.compute.domain.NovaFlavor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.core.io.ClassPathResource
import org.springframework.scheduling.TaskScheduler
import org.springframework.test.context.ActiveProfiles

import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
@ActiveProfiles("unittest")
class HttpControllersTests(
    @Autowired val mockMvc: MockMvc
    ) {

    @MockkBean
    private lateinit var openstackService: OpenStackService

    @MockkBean
    private lateinit var templateService: TemplateService

    @MockkBean
    private lateinit var ucloudService: UCloudClient

    @Qualifier("taskscheduler")
    @MockkBean
    lateinit var task: TaskScheduler

    @MockkBean
    lateinit var job: Jobs

    //Find a way to test without getting tokens from ucloud?
//    private val authHeader =
//        """
//        """.trimIndent()

    @Test
    fun `Test test`() {

        every { openstackService.chargeAllStacks() } returns Unit

        mockMvc.perform(
            get("/charge"))
            .andExpect(status().isOk)
            .andExpect(content().string("charge all jobs!"))
    }

//    FIXME This should be tested
//    @Test
//    fun `Given no bearer token expect unauthorized access response`() {
//        mockMvc.perform(
//            get("/ucloud/testcenter/jobs/retrieveProducts"))
//            .andExpect(status().isUnauthorized)
//    }

    @Test
    fun `Given bearer token expect 200 access response`() {
        every { openstackService.listFlavors() } returns emptyList()

        mockMvc.perform(
            get("/ucloud/testcenter/jobs/retrieveProducts")
                //.header(HttpHeaders.AUTHORIZATION, authHeader)
        )
            .andExpect(status().isOk)
    }

    @Test
    fun `Retrieve products`() {
        val expectedProductOutput = ClassPathResource("products.json").file.readText()

        val flavorsJson =
            """
            [
            {
              "id": "1195aefc-09fb-4bc7-b1f0-f21a304e864c",
              "name": "small 8GB"
            },
            {
              "id": "2295aefc-09fb-4bc7-b1f0-f21a304e864c",
              "name": "medium 16GB"
            },
            {
              "id": "3395aefc-09fb-4bc7-b1f0-f21a304e864c",
              "name": "large 32GB"
            },
            {
              "id": "4495aefc-09fb-4bc7-b1f0-f21a304e864c",
              "name": "uc-t4-1"
            }
            ]
            """

        val mapper = jacksonObjectMapper()
        val flavors: List<NovaFlavor> = mapper.readValue(flavorsJson)

        every { openstackService.listFlavors() } returns flavors

        every { openstackService.getFlavorExtraSpecs(
            "1195aefc-09fb-4bc7-b1f0-f21a304e864c")
        } returns mutableMapOf("category" to "standard")
        every { openstackService.getFlavorExtraSpecs(
            "2295aefc-09fb-4bc7-b1f0-f21a304e864c")
        } returns mutableMapOf("category" to "standard")
        every { openstackService.getFlavorExtraSpecs(
            "3395aefc-09fb-4bc7-b1f0-f21a304e864c")
        } returns mutableMapOf("category" to "standard")
        every { openstackService.getFlavorExtraSpecs(
            "4495aefc-09fb-4bc7-b1f0-f21a304e864c")
        } returns mutableMapOf("category" to "gpu")

        mockMvc.perform(
            get("/ucloud/testcenter/jobs/retrieveProducts")
                //.header(HttpHeaders.AUTHORIZATION, authHeader)
        )
            .andExpect(status().isOk)
            .andExpect(content().json(expectedProductOutput))
    }

    @Test
    fun `Create job`() {
        // Do something like this to mock service call
        //val template1 = Template("Template1", "Content of template 1")

        //override fun onLocationMeasured(location: Location) { ... }

        every { openstackService.createStacks(any())} returns Unit
        every { openstackService.sendStatusWhenStackComplete(any())} returns Unit

        val jobJSON = ClassPathResource("jobs.json").file.readText()

        mockMvc.perform(post("/ucloud/testcenter/jobs")
            .contentType("application/json")
            .content(jobJSON))
            .andExpect(status().isOk);

//        mockMvc.perform(post("/ucloud/claaudia/compute/jobs/dav").accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk)
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))



    }

}