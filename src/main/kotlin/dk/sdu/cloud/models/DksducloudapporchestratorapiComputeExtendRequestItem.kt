package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import dk.sdu.cloud.models.modified.Job
import javax.validation.Valid

/**
 *
 * @param job
 * @param requestedTime
 */
data class DksducloudapporchestratorapiComputeExtendRequestItem(

    @field:Valid
    @field:JsonProperty("job") val job: Job? = null,

    @field:Valid
    @field:JsonProperty("requestedTime") val requestedTime: DksducloudappstoreapiSimpleDuration? = null
) {

}

