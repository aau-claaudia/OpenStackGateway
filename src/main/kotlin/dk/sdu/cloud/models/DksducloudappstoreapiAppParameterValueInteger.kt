package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * An integral value  Internally this uses a big integer type and there are no defined limits.
 * @param value
 * @param type
 */
data class DksducloudappstoreapiAppParameterValueInteger(

    @field:JsonProperty("value") val value: kotlin.Long? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiAppParameterValueInteger.Type? = null
) {

    /**
     *
     * Values: integer
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("integer")
        integer("integer");

    }

}

