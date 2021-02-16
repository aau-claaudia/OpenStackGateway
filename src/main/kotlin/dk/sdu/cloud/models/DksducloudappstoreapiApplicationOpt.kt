package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import dk.sdu.cloud.models.modified.ApplicationInvocationDescription
import javax.validation.Valid

/**
 *
 * @param metadata
 * @param invocation
 */
data class DksducloudappstoreapiApplicationOpt(

    @field:Valid
    @field:JsonProperty("metadata") val metadata: DksducloudappstoreapiApplicationMetadata? = null,

    @field:Valid
    @field:JsonProperty("invocation") val invocation: ApplicationInvocationDescription? = null
) {

}

