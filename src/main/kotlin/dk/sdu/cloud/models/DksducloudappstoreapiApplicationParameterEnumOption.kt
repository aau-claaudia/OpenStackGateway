package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param name
 * @param value
 */
data class DksducloudappstoreapiApplicationParameterEnumOption(

    @field:JsonProperty("name") val name: kotlin.String? = null,

    @field:JsonProperty("value") val value: kotlin.String? = null
) {

}

