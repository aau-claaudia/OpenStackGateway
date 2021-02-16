package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param value
 * @param type
 */
data class DksducloudappstoreapiAppParameterValueFloatingPointOpt(

    @field:JsonProperty("value") val value: java.math.BigDecimal? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiAppParameterValueFloatingPointOpt.Type? = null
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

