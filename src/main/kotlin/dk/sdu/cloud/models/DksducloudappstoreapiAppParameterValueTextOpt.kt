package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param value
 * @param type
 */
data class DksducloudappstoreapiAppParameterValueTextOpt(

    @field:JsonProperty("value") val value: kotlin.String? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiAppParameterValueTextOpt.Type? = null
) {

    /**
     *
     * Values: text
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("text")
        text("text");

    }

}

