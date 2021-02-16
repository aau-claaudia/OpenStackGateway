package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 *
 * @param name
 * @param title
 * @param description
 * @param suggestedApplication
 * @param defaultValue
 * @param optional
 * @param type
 */
data class DksducloudappstoreapiApplicationParameterPeerOpt(

    @field:JsonProperty("name") val name: kotlin.String? = null,

    @field:JsonProperty("title") val title: kotlin.String? = null,

    @field:JsonProperty("description") val description: kotlin.String? = null,

    @field:JsonProperty("suggestedApplication") val suggestedApplication: kotlin.String? = null,

    @field:Valid
    @field:JsonProperty("defaultValue") val defaultValue: kotlin.Any? = null,

    @field:JsonProperty("optional") val optional: kotlin.Boolean? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiApplicationParameterPeerOpt.Type? = null
) {

    /**
     *
     * Values: peer
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("peer")
        peer("peer");

    }

}

