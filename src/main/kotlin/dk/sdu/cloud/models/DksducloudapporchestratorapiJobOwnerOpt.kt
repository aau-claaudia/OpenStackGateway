package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param launchedBy The username of the user which started the job
 * @param project The project ID of the project which owns this job  This value can be null and this signifies that the job belongs to the personal workspace of the user.
 */
data class DksducloudapporchestratorapiJobOwnerOpt(

    @field:JsonProperty("launchedBy") val launchedBy: kotlin.String? = null,

    @field:JsonProperty("project") val project: kotlin.String? = null
) {

}

