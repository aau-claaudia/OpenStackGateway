package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param creditsCharged The amount of credits charged to the `owner` of this job
 * @param pricePerUnit The unit price of this job
 */
data class DksducloudapporchestratorapiJobBilling(

    @field:JsonProperty("creditsCharged") val creditsCharged: kotlin.Long? = null,

    @field:JsonProperty("pricePerUnit") val pricePerUnit: kotlin.Long? = null
) {

}

