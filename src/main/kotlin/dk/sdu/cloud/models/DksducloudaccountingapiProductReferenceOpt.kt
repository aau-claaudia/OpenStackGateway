package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param id
 * @param category
 * @param provider
 */
data class DksducloudaccountingapiProductReferenceOpt(

    @field:JsonProperty("id") val id: kotlin.String? = null,

    @field:JsonProperty("category") val category: kotlin.String? = null,

    @field:JsonProperty("provider") val provider: kotlin.String? = null
) {

}

