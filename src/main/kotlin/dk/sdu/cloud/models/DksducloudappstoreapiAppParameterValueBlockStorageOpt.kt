package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param id
 * @param type
 */
data class DksducloudappstoreapiAppParameterValueBlockStorageOpt(

    @field:JsonProperty("id") val id: kotlin.String? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiAppParameterValueBlockStorageOpt.Type? = null
) {

    /**
     *
     * Values: blockStorage
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("block_storage")
        blockStorage("block_storage");

    }

}

