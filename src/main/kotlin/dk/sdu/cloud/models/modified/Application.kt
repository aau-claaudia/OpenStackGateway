package dk.sdu.cloud.models.modified

import com.fasterxml.jackson.annotation.JsonProperty
import dk.sdu.cloud.models.DksducloudappstoreapiApplicationMetadata
import javax.validation.Valid

/**
 *
 * @param metadata
 * @param invocation
 */
data class Application(

    @field:Valid
    @field:JsonProperty("metadata") val metadata: DksducloudappstoreapiApplicationMetadata? = null,

    @field:Valid
    @field:JsonProperty("invocation") val invocation: ApplicationInvocationDescription? = null
) {

}

