package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A reference to block storage
 * @param id
 * @param type
 */
data class DksducloudappstoreapiAppParameterValueNetwork(

    @field:JsonProperty("id") val id: kotlin.String? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiAppParameterValueNetwork.Type? = null
) {

    /**
     *
     * Values: network
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("network")
        network("network");

    }

}

