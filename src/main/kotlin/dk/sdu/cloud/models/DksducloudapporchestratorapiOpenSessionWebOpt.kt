package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param jobId
 * @param rank
 * @param redirectClientTo
 * @param type
 */
data class DksducloudapporchestratorapiOpenSessionWebOpt(

    @field:JsonProperty("jobId") val jobId: kotlin.String? = null,

    @field:JsonProperty("rank") val rank: kotlin.Int? = null,

    @field:JsonProperty("redirectClientTo") val redirectClientTo: kotlin.String? = null,

    @field:JsonProperty("type") val type: DksducloudapporchestratorapiOpenSessionWebOpt.Type? = null
) {

    /**
     *
     * Values: web
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("web")
        web("web");

    }

}

