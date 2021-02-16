package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param type
 */
data class DksducloudaccountingapiProductAvailabilityAvailableOpt(

    @field:JsonProperty("type") val type: DksducloudaccountingapiProductAvailabilityAvailableOpt.Type? = null
) {

    /**
     *
     * Values: available
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("available")
        available("available");

    }

}

