package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param enabled
 * @param web
 * @param vnc
 * @param batch
 * @param logs
 * @param terminal
 * @param peers
 */
data class DksducloudapporchestratorapiManifestFeatureSupportComputeDockerOpt(

    @field:JsonProperty("enabled") val enabled: kotlin.Boolean? = null,

    @field:JsonProperty("web") val web: kotlin.Boolean? = null,

    @field:JsonProperty("vnc") val vnc: kotlin.Boolean? = null,

    @field:JsonProperty("batch") val batch: kotlin.Boolean? = null,

    @field:JsonProperty("logs") val logs: kotlin.Boolean? = null,

    @field:JsonProperty("terminal") val terminal: kotlin.Boolean? = null,

    @field:JsonProperty("peers") val peers: kotlin.Boolean? = null
) {

}

