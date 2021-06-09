package dk.aau.claaudia.openstackgateway

import com.ninjasquad.springmockk.MockkBean
import dk.aau.claaudia.openstackgateway.services.OpenStackService
import dk.aau.claaudia.openstackgateway.services.TemplateService
//import dk.aau.claaudia.openstackgateway.services.UCloudService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.core.io.ClassPathResource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@WebMvcTest
class HttpControllersTests(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var openStackService: OpenStackService

    @MockkBean
    private lateinit var templateService: TemplateService

//    @MockkBean
//    private lateinit var ucloudService: UCloudService


    @Test
    fun `Create job`() {
        // Do something like this to mock service call
        //val template1 = Template("Template1", "Content of template 1")

        //every { templateRepository.findByName(template1.name) } returns template1

        val jobJSON = ClassPathResource("job.json").file.readText()

        mockMvc.perform(post("/ucloud/claaudia/compute/jobs/dav")
            .contentType("application/json")
            .content(jobJSON))
            //.andExpect(status().isOk);

//        mockMvc.perform(post("/ucloud/claaudia/compute/jobs/dav").accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk)
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))



    }

}