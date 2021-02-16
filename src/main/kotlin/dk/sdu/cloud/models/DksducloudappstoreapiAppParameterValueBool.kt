package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A boolean value
 * @param value
 * @param type
 */
data class DksducloudappstoreapiAppParameterValueBool(

    @field:JsonProperty("value") val value: kotlin.Boolean? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiAppParameterValueBool.Type? = null
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

