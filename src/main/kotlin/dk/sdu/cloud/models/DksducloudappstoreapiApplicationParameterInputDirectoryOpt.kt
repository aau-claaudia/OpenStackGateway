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
 * @param type
 */
data class DksducloudappstoreapiApplicationParameterInputDirectoryOpt(

    @field:JsonProperty("name") val name: kotlin.String? = null,

    @field:JsonProperty("optional") val optional: kotlin.Boolean? = null,

    @field:Valid
    @field:JsonProperty("defaultValue") val defaultValue: kotlin.Any? = null,

    @field:JsonProperty("title") val title: kotlin.String? = null,

    @field:JsonProperty("description") val description: kotlin.String? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiApplicationParameterInputDirectoryOpt.Type? = null
) {

    /**
     *
     * Values: inputDirectory
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("input_directory")
        inputDirectory("input_directory");

    }

}

