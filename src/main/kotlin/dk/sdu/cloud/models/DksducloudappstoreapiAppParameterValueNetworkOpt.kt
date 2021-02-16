package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param id
 * @param type
 */
data class DksducloudappstoreapiAppParameterValueNetworkOpt(

    @field:JsonProperty("id") val id: kotlin.String? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiAppParameterValueNetworkOpt.Type? = null
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

