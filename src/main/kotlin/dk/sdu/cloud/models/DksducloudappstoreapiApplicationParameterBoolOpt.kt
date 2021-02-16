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
 * @param trueValue
 * @param falseValue
 * @param type
 */
data class DksducloudappstoreapiApplicationParameterBoolOpt(

    @field:JsonProperty("name") val name: kotlin.String? = null,

    @field:JsonProperty("optional") val optional: kotlin.Boolean? = null,

    @field:Valid
    @field:JsonProperty("defaultValue") val defaultValue: kotlin.Any? = null,

    @field:JsonProperty("title") val title: kotlin.String? = null,

    @field:JsonProperty("description") val description: kotlin.String? = null,

    @field:JsonProperty("trueValue") val trueValue: kotlin.String? = null,

    @field:JsonProperty("falseValue") val falseValue: kotlin.String? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiApplicationParameterBoolOpt.Type? = null
) {

    /**
     *
     * Values: boolean
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("boolean")
        boolean("boolean");

    }

}

