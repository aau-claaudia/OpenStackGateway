package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 *
 * @param docker
 * @param virtualMachine
 */
data class DksducloudapporchestratorapiManifestFeatureSupportComputeOpt(

    @field:Valid
    @field:JsonProperty("docker") val docker: DksducloudapporchestratorapiManifestFeatureSupportComputeDocker? = null,

    @field:Valid
    @field:JsonProperty("virtualMachine") val virtualMachine: DksducloudapporchestratorapiManifestFeatureSupportComputeVirtualMachine? = null
) {

}

