package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 *
 * @param sessions
 */
data class DksducloudapporchestratorapiComputeOpenInteractiveSessionResponse(

    @field:Valid
    @field:JsonProperty("sessions") val sessions: kotlin.collections.List<DkSduCloudAppOrchestratorApiComputeOpenInteractiveSessionResponseSessions>? = null
) {

}

