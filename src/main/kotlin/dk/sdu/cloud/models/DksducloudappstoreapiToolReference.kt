package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 *
 * @param name
 * @param version
 * @param tool
 */
data class DksducloudappstoreapiToolReference(

    @field:JsonProperty("name") val name: kotlin.String? = null,

    @field:JsonProperty("version") val version: kotlin.String? = null,

    @field:Valid
    @field:JsonProperty("tool") val tool: DksducloudappstoreapiToolOpt? = null
) {

}

