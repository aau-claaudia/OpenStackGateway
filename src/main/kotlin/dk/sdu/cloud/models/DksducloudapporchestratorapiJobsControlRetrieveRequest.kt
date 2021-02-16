package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param id
 * @param includeParameters
 * @param includeUpdates
 * @param includeApplication
 * @param includeProduct
 */
data class DksducloudapporchestratorapiJobsControlRetrieveRequest(

    @field:JsonProperty("id") val id: kotlin.String? = null,

    @field:JsonProperty("includeParameters") val includeParameters: kotlin.Boolean? = null,

    @field:JsonProperty("includeUpdates") val includeUpdates: kotlin.Boolean? = null,

    @field:JsonProperty("includeApplication") val includeApplication: kotlin.Boolean? = null,

    @field:JsonProperty("includeProduct") val includeProduct: kotlin.Boolean? = null
) {

}

