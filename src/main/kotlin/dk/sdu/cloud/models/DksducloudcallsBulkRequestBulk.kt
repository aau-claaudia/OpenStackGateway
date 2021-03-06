package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 *
 * @param items
 * @param type
 */
data class DksducloudcallsBulkRequestBulk(

    @field:Valid
    @field:JsonProperty("items") val items: kotlin.collections.List<kotlin.Any>? = null,

    @field:JsonProperty("type") val type: DksducloudcallsBulkRequestBulk.Type? = null
) {

    /**
     *
     * Values: bulk
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("bulk")
        bulk("bulk");

    }

}

