package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A reference to a UCloud file  The path to the file most always be absolute an refers to either a UCloud directory or file.
 * @param path
 * @param readOnly
 * @param type
 */
data class DksducloudappstoreapiAppParameterValueFile(

    @field:JsonProperty("path") val path: kotlin.String? = null,

    @field:JsonProperty("readOnly") val readOnly: kotlin.Boolean? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiAppParameterValueFile.Type? = null
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

