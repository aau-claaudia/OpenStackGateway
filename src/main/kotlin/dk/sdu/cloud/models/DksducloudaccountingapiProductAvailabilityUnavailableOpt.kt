package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param reason
 * @param type
 */
data class DksducloudaccountingapiProductAvailabilityUnavailableOpt(

    @field:JsonProperty("reason") val reason: kotlin.String? = null,

    @field:JsonProperty("type") val type: DksducloudaccountingapiProductAvailabilityUnavailableOpt.Type? = null
) {

    /**
     *
     * Values: unavailable
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("unavailable")
        unavailable("unavailable");

    }

}

