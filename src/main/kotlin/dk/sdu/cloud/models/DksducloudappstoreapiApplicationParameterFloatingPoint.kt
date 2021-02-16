package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 *
 * @param name
 * @param optional
 * @param defaultValue
 * @param title
 * @param description
 * @param min
 * @param max
 * @param step
 * @param unitName
 * @param type
 */
data class DksducloudappstoreapiApplicationParameterFloatingPoint(

    @field:JsonProperty("name") val name: kotlin.String? = null,

    @field:JsonProperty("optional") val optional: kotlin.Boolean? = null,

    @field:Valid
    @field:JsonProperty("defaultValue") val defaultValue: kotlin.Any? = null,

    @field:JsonProperty("title") val title: kotlin.String? = null,

    @field:JsonProperty("description") val description: kotlin.String? = null,

    @field:JsonProperty("min") val min: java.math.BigDecimal? = null,

    @field:JsonProperty("max") val max: java.math.BigDecimal? = null,

    @field:JsonProperty("step") val step: java.math.BigDecimal? = null,

    @field:JsonProperty("unitName") val unitName: kotlin.String? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiApplicationParameterFloatingPoint.Type? = null
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

