package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param type
 */
data class DksducloudaccountingapiProductAvailabilityAvailable(

    @field:JsonProperty("type") val type: DksducloudaccountingapiProductAvailabilityAvailable.Type? = null
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

