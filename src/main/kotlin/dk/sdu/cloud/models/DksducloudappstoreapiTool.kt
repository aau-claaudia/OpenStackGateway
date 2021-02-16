package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 *
 * @param owner
 * @param createdAt
 * @param modifiedAt
 * @param description
 */
data class DksducloudappstoreapiTool(

    @field:JsonProperty("owner") val owner: kotlin.String? = null,

    @field:JsonProperty("createdAt") val createdAt: kotlin.Long? = null,

    @field:JsonProperty("modifiedAt") val modifiedAt: kotlin.Long? = null,

    @field:Valid
    @field:JsonProperty("description") val description: DksducloudappstoreapiNormalizedToolDescription? = null
) {

}

