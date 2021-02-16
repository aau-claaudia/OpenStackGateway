package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param jobId
 * @param filePath
 */
data class DksducloudapporchestratorapiJobsControlSubmitFileRequestOpt(

    @field:JsonProperty("jobId") val jobId: kotlin.String? = null,

    @field:JsonProperty("filePath") val filePath: kotlin.String? = null
) {

}

