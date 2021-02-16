package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A reference to a separate UCloud job  The compute provider should use this information to make sure that the two jobs can communicate with each other.
 * @param hostname
 * @param jobId
 * @param type
 */
data class DksducloudappstoreapiAppParameterValuePeer(

    @field:JsonProperty("hostname") val hostname: kotlin.String? = null,

    @field:JsonProperty("jobId") val jobId: kotlin.String? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiAppParameterValuePeer.Type? = null
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

