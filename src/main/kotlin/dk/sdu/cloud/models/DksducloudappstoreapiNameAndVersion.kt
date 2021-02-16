package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param name
 * @param version
 */
data class DksducloudappstoreapiNameAndVersion(

    @field:JsonProperty("name") val name: kotlin.String? = null,

    @field:JsonProperty("version") val version: kotlin.String? = null
) {

}

