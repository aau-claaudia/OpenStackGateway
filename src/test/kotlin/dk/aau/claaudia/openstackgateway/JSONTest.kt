//package dk.aau.claaudia.openstackgateway
//
//import dk.sdu.cloud.models.modified.Job
//import dk.sdu.cloud.models.modified.JobSpecification
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.jupiter.api.Test
//import org.springframework.boot.test.json.JacksonTester
//
//import org.springframework.boot.test.autoconfigure.json.JsonTest
//import org.springframework.core.io.ClassPathResource
//import java.lang.Exception
//import javax.annotation.Resource
//
//
//@JsonTest
//class MyJsonTests {
//    @Resource
//    private val json: JacksonTester<Job>? = null
//
//    //FIXME Expand these tests
//    @Test
//    @Throws(Exception::class)
//    fun testSerialize() {
//        val job = Job("JobId123", null, null, null, JobSpecification(), null, null)
//
//        assertThat(json!!.write(job)).hasJsonPathStringValue("@.id")
//        assertThat(json.write(job)).extractingJsonPathStringValue("@.id")
//            .isEqualTo("JobId123")
//    }
//
//    //FIXME Expand these tests
//    @Test
//    @Throws(Exception::class)
//    fun testDeserialize() {
//        val jobJSON = ClassPathResource("job.json").file.readBytes()
//        val job: Job = json!!.parse(jobJSON).getObject()
//
//        assertThat(job.id).isEqualTo("string")
//        //assertThat(json!!.parseObject(content).getMake()).isEqualTo("Ford")
//    }
//}