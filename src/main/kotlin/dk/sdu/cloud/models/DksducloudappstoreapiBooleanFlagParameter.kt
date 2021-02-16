package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param variableName
 * @param flag
 * @param type
 */
data class DksducloudappstoreapiBooleanFlagParameter(

    @field:JsonProperty("variableName") val variableName: kotlin.String? = null,

    @field:JsonProperty("flag") val flag: kotlin.String? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiBooleanFlagParameter.Type? = null
) {

    /**
     *
     * Values: boolFlag
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("bool_flag")
        boolFlag("bool_flag");

    }

}

