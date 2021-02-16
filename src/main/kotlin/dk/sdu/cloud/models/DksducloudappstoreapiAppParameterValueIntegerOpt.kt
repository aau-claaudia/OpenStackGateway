package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param value
 * @param type
 */
data class DksducloudappstoreapiAppParameterValueIntegerOpt(

    @field:JsonProperty("value") val value: kotlin.Long? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiAppParameterValueIntegerOpt.Type? = null
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

