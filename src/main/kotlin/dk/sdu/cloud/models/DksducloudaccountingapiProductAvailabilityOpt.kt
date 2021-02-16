package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 *
 * @param type
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes(
    JsonSubTypes.Type(value = DksducloudaccountingapiProductAvailabilityAvailable::class, name = "available"),
    JsonSubTypes.Type(value = DksducloudaccountingapiProductAvailabilityUnavailable::class, name = "unavailable")
)

interface DksducloudaccountingapiProductAvailabilityOpt {

    val type: DksducloudaccountingapiProductAvailabilityOpt.Type?

    /**
     *
     * Values: available,unavailable
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("available")
        available("available"),

        @JsonProperty("unavailable")
        unavailable("unavailable");

    }

}

