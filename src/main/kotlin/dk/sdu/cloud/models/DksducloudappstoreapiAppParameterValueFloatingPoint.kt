package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A floating point value  Internally this uses a big decimal type and there are no defined limits.
 * @param value
 * @param type
 */
data class DksducloudappstoreapiAppParameterValueFloatingPoint(

    @field:JsonProperty("value") val value: java.math.BigDecimal? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiAppParameterValueFloatingPoint.Type? = null
) {

    /**
     *
     * Values: floatingPoint
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("floating_point")
        floatingPoint("floating_point");

    }

}

