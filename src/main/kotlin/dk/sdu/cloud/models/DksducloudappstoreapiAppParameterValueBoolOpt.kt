package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param value
 * @param type
 */
data class DksducloudappstoreapiAppParameterValueBoolOpt(

    @field:JsonProperty("value") val value: kotlin.Boolean? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiAppParameterValueBoolOpt.Type? = null
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

