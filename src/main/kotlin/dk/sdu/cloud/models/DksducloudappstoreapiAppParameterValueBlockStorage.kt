package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A reference to block storage
 * @param id
 * @param type
 */
data class DksducloudappstoreapiAppParameterValueBlockStorage(

    @field:JsonProperty("id") val id: kotlin.String? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiAppParameterValueBlockStorage.Type? = null
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

