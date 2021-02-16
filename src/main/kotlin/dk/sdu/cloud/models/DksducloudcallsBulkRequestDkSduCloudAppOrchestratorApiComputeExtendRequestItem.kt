package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 *
 * @param type
 * @param items
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes(
    JsonSubTypes.Type(value = DksducloudcallsBulkRequestBulk::class, name = "bulk")
)

interface DksducloudcallsBulkRequestDkSduCloudAppOrchestratorApiComputeExtendRequestItem {

    val type: DksducloudcallsBulkRequestDkSduCloudAppOrchestratorApiComputeExtendRequestItem.Type?

    val items: kotlin.collections.List<kotlin.Any>?

    /**
     *
     * Values: bulk
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("bulk")
        bulk("bulk");

    }

}

