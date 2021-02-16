package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 *
 * @param compute
 */
data class DksducloudapporchestratorapiManifestFeatureSupport(

    @field:Valid
    @field:JsonProperty("compute") val compute: DksducloudapporchestratorapiManifestFeatureSupportCompute? = null
) {

}

