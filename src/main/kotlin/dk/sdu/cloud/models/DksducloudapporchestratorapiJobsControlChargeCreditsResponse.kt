package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 *
 * @param insufficientFunds A list of jobs which could not be charged due to lack of funds. If all jobs were charged successfully then this will empty.
 * @param duplicateCharges A list of jobs which could not be charged due to it being a duplicate charge. If all jobs were charged successfully this will be empty.
 */
data class DksducloudapporchestratorapiJobsControlChargeCreditsResponse(

    @field:Valid
    @field:JsonProperty("insufficientFunds") val insufficientFunds: kotlin.collections.List<DkSduCloudAppOrchestratorApiJobsControlChargeCreditsResponseInsufficientFunds>? = null,

    @field:Valid
    @field:JsonProperty("duplicateCharges") val duplicateCharges: kotlin.collections.List<DkSduCloudAppOrchestratorApiJobsControlChargeCreditsResponseInsufficientFunds>? = null
) {

}

