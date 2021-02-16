package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param enabled
 * @param logs
 * @param vnc
 * @param terminal
 */
data class DksducloudapporchestratorapiManifestFeatureSupportComputeVirtualMachineOpt(

    @field:JsonProperty("enabled") val enabled: kotlin.Boolean? = null,

    @field:JsonProperty("logs") val logs: kotlin.Boolean? = null,

    @field:JsonProperty("vnc") val vnc: kotlin.Boolean? = null,

    @field:JsonProperty("terminal") val terminal: kotlin.Boolean? = null
) {

}

