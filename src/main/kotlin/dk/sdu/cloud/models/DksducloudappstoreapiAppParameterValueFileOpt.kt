package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param path
 * @param readOnly
 * @param type
 */
data class DksducloudappstoreapiAppParameterValueFileOpt(

    @field:JsonProperty("path") val path: kotlin.String? = null,

    @field:JsonProperty("readOnly") val readOnly: kotlin.Boolean? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiAppParameterValueFileOpt.Type? = null
) {

    /**
     *
     * Values: file
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("file")
        file("file");

    }

}

