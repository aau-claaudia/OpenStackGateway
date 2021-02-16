package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param jobId
 * @param rank
 * @param url
 * @param password
 * @param type
 */
data class DksducloudapporchestratorapiOpenSessionVncOpt(

    @field:JsonProperty("jobId") val jobId: kotlin.String? = null,

    @field:JsonProperty("rank") val rank: kotlin.Int? = null,

    @field:JsonProperty("url") val url: kotlin.String? = null,

    @field:JsonProperty("password") val password: kotlin.String? = null,

    @field:JsonProperty("type") val type: DksducloudapporchestratorapiOpenSessionVncOpt.Type? = null
) {

    /**
     *
     * Values: vnc
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("vnc")
        vnc("vnc");

    }

}

