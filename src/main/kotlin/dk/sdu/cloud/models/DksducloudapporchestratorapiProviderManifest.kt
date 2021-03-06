package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 *
 * @param features
 */
data class DksducloudapporchestratorapiProviderManifest(

    @field:Valid
    @field:JsonProperty("features") val features: DksducloudapporchestratorapiManifestFeatureSupport? = null
) {

}

