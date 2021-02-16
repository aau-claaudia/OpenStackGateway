package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param hostname
 * @param jobId
 * @param type
 */
data class DksducloudappstoreapiAppParameterValuePeerOpt(

    @field:JsonProperty("hostname") val hostname: kotlin.String? = null,

    @field:JsonProperty("jobId") val jobId: kotlin.String? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiAppParameterValuePeerOpt.Type? = null
) {

    /**
     *
     * Values: peer
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("peer")
        peer("peer");

    }

}

