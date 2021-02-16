package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param jobId
 * @param rank
 * @param sessionIdentifier
 * @param type
 */
data class DksducloudapporchestratorapiOpenSessionShell(

    @field:JsonProperty("jobId") val jobId: kotlin.String? = null,

    @field:JsonProperty("rank") val rank: kotlin.Int? = null,

    @field:JsonProperty("sessionIdentifier") val sessionIdentifier: kotlin.String? = null,

    @field:JsonProperty("type") val type: DksducloudapporchestratorapiOpenSessionShell.Type? = null
) {

    /**
     *
     * Values: shell
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("shell")
        shell("shell");

    }

}

