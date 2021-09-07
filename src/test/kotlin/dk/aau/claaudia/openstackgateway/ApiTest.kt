package dk.aau.claaudia.openstackgateway

import com.ninjasquad.springmockk.MockkBean
import dk.aau.claaudia.openstackgateway.services.OpenStackService
import dk.aau.claaudia.openstackgateway.services.TemplateService
import dk.aau.claaudia.openstackgateway.tasks.Jobs
import dk.sdu.cloud.providers.UCloudClient
import io.mockk.every
//import dk.aau.claaudia.openstackgateway.services.UCloudService
import org.junit.jupiter.api.Test
import org.openstack4j.api.Builders
import org.openstack4j.model.heat.Stack
import org.openstack4j.model.heat.Template
import org.openstack4j.openstack.common.GenericLink
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.task.TaskSchedulerBuilder
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpHeaders
import org.springframework.scheduling.TaskScheduler
import org.springframework.test.context.ActiveProfiles

import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.Base64Utils

@WebMvcTest
@ActiveProfiles("unittest")
class HttpControllersTests(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var openStackService: OpenStackService

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

        every { openStackService.getAllStacks()} returns Unit

        mockMvc.perform(
            get("/nogettest"))
            .andExpect(status().isOk)
            .andExpect(content().string("Dette er bare test"))
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
        mockMvc.perform(
            get("/ucloud/testcenter/jobs/retrieveProducts")
                //.header(HttpHeaders.AUTHORIZATION, authHeader)
        )
            .andExpect(status().isOk)
    }

    @Test
    fun `Retrieve products`() {
        val expectedProducts = ClassPathResource("products.json").file.readText()

        mockMvc.perform(
            get("/ucloud/testcenter/jobs/retrieveProducts")
                //.header(HttpHeaders.AUTHORIZATION, authHeader)
        )
            .andExpect(status().isOk)
            .andExpect(content().json(expectedProducts))
    }

    @Test
    fun `Create job`() {
        // Do something like this to mock service call
        //val template1 = Template("Template1", "Content of template 1")

        //override fun onLocationMeasured(location: Location) { ... }

        val stack = object : Stack {
            override fun getId(): String {
                TODO("Not yet implemented")
            }

            override fun getName(): String {
                TODO("Not yet implemented")
            }

            override fun getStatus(): String {
                TODO("Not yet implemented")
            }

            override fun getStackStatusReason(): String {
                TODO("Not yet implemented")
            }

            override fun getDescription(): String {
                TODO("Not yet implemented")
            }

            override fun getTemplateDescription(): String {
                TODO("Not yet implemented")
            }

            override fun getTimeoutMins(): Long {
                TODO("Not yet implemented")
            }

            override fun getOutputs(): MutableList<MutableMap<String, Any>> {
                TODO("Not yet implemented")
            }

            override fun getParameters(): MutableMap<String, String> {
                TODO("Not yet implemented")
            }

            override fun getCreationTime(): String {
                TODO("Not yet implemented")
            }

            override fun getLinks(): MutableList<GenericLink> {
                TODO("Not yet implemented")
            }

            override fun getUpdatedTime(): String {
                TODO("Not yet implemented")
            }

            override fun getTags(): MutableList<String> {
                TODO("Not yet implemented")
            }

        }

        every { openStackService.createStacks(any())} returns mutableListOf(stack)
        every { openStackService.monitorStackCreations(any())} returns Unit

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