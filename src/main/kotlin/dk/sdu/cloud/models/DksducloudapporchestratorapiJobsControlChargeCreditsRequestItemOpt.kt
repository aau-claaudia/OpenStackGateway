package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 *
 * @param id The ID of the job
 * @param chargeId The ID of the charge  This charge ID must be unique for the job, UCloud will reject charges which are not unique.
 * @param wallDuration
 */
data class DksducloudapporchestratorapiJobsControlChargeCreditsRequestItemOpt(

    @field:JsonProperty("id") val id: kotlin.String? = null,

    @field:JsonProperty("chargeId") val chargeId: kotlin.String? = null,

    @field:Valid
    @field:JsonProperty("wallDuration") val wallDuration: DksducloudappstoreapiSimpleDuration? = null
) {

}

