package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import dk.sdu.cloud.models.modified.Job
import javax.validation.Valid

/**
 *
 * @param job
 * @param rank
 * @param sessionType
 */
data class DksducloudapporchestratorapiComputeOpenInteractiveSessionRequestItem(

    @field:Valid
    @field:JsonProperty("job") val job: Job? = null,

    @field:JsonProperty("rank") val rank: kotlin.Int? = null,

    @field:JsonProperty("sessionType") val sessionType: DksducloudapporchestratorapiComputeOpenInteractiveSessionRequestItem.SessionType? = null
) {

    /**
     *
     * Values: wEB,vNC,sHELL
     */
    enum class SessionType(val value: kotlin.String) {

        @JsonProperty("WEB")
        wEB("WEB"),

        @JsonProperty("VNC")
        vNC("VNC"),

        @JsonProperty("SHELL")
        sHELL("SHELL");

    }

}

