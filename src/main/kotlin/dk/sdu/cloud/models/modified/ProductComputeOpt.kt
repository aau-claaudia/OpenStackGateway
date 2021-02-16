package dk.sdu.cloud.models.modified

import com.fasterxml.jackson.annotation.JsonProperty
import dk.sdu.cloud.models.DksducloudaccountingapiProductCategoryId
import javax.validation.Valid

/**
 *
 * @param id
 * @param pricePerUnit
 * @param category
 * @param description
 * @param availability
 * @param priority
 * @param cpu
 * @param memoryInGigs
 * @param gpu
 */
data class ProductComputeOpt(

    @field:JsonProperty("id") val id: kotlin.String? = null,

    @field:JsonProperty("pricePerUnit") val pricePerUnit: kotlin.Long? = null,

    @field:Valid
    @field:JsonProperty("category") val category: DksducloudaccountingapiProductCategoryId? = null,

    @field:JsonProperty("description") val description: kotlin.String? = null,

    @field:Valid
    @field:JsonProperty("availability") val availability: ProductAvailability? = null,

    @field:JsonProperty("priority") val priority: kotlin.Int? = null,

    @field:JsonProperty("cpu") val cpu: kotlin.Int? = null,

    @field:JsonProperty("memoryInGigs") val memoryInGigs: kotlin.Int? = null,

    @field:JsonProperty("gpu") val gpu: kotlin.Int? = null
) {

}

