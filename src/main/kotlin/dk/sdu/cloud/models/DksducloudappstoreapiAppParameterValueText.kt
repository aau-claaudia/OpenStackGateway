package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A textual value
 * @param value
 * @param type
 */
data class DksducloudappstoreapiAppParameterValueText(

    @field:JsonProperty("value") val value: kotlin.String? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiAppParameterValueText.Type? = null
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

