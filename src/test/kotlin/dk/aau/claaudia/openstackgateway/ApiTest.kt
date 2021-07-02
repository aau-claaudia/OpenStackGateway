package dk.aau.claaudia.openstackgateway

import com.ninjasquad.springmockk.MockkBean
import dk.aau.claaudia.openstackgateway.services.OpenStackService
import dk.aau.claaudia.openstackgateway.services.TemplateService
import dk.sdu.cloud.providers.UCloudClient
//import dk.aau.claaudia.openstackgateway.services.UCloudService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpHeaders
import org.springframework.test.context.ActiveProfiles

import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.Base64Utils

@WebMvcTest
@ActiveProfiles("test")
class HttpControllersTests(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var openStackService: OpenStackService

    @MockkBean
    private lateinit var templateService: TemplateService

    @MockkBean
    private lateinit var ucloudService: UCloudClient

    //Find a way to test without getting tokens from ucloud?
//    private val authHeader =
//        """
//        """.trimIndent()

    @Test
    fun `Test test`() {
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

        //every { templateRepository.findByName(template1.name) } returns template1

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