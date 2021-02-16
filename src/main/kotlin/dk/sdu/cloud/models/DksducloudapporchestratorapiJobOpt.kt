package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import dk.sdu.cloud.models.modified.JobSpecification
import javax.validation.Valid

/**
 *
 * @param id Unique identifier for this job.  UCloud guarantees that no other job, regardless of compute provider, has the same unique identifier.
 * @param owner
 * @param updates A list of status updates from the compute backend.  The status updates tell a story of what happened with the job. This list is ordered by the timestamp in ascending order. The current state of the job will always be the last element. `updates` is guaranteed to always contain at least one element.
 * @param billing
 * @param parameters
 * @param status
 * @param output
 */
data class DksducloudapporchestratorapiJobOpt(

    @field:JsonProperty("id") val id: kotlin.String? = null,

    @field:Valid
    @field:JsonProperty("owner") val owner: DksducloudapporchestratorapiJobOwner? = null,

    @field:Valid
    @field:JsonProperty("updates") val updates: kotlin.collections.List<DkSduCloudAppOrchestratorApiJobUpdates>? = null,

    @field:Valid
    @field:JsonProperty("billing") val billing: DksducloudapporchestratorapiJobBilling? = null,

    @field:Valid
    @field:JsonProperty("parameters") val parameters: JobSpecification? = null,

    @field:Valid
    @field:JsonProperty("status") val status: DksducloudapporchestratorapiJobStatus? = null,

    @field:Valid
    @field:JsonProperty("output") val output: DksducloudapporchestratorapiJobOutputOpt? = null
) {

}

