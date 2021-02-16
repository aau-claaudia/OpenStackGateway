package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param outputFolder
 */
data class DksducloudapporchestratorapiJobOutput(

    @field:JsonProperty("outputFolder") val outputFolder: kotlin.String? = null
) {

}

