package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 *
 * @param items
 * @param type
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes(
    JsonSubTypes.Type(value = DksducloudcallsBulkRequestBulk::class, name = "bulk")
)

interface DksducloudcallsBulkRequest {

    val items: kotlin.collections.List<kotlin.Any>?

    val type: DksducloudcallsBulkRequest.Type?

    /**
     *
     * Values: bulk
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("bulk")
        bulk("bulk");

    }

}

