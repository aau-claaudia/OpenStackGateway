package dk.sdu.cloud.models.modified

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 *
 * @param type
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes(
    JsonSubTypes.Type(value = Available::class, name = "available"),
    JsonSubTypes.Type(value = Unavailable::class, name = "unavailable")
)

sealed class ProductAvailability {
    abstract val type: String
}

class Available() : ProductAvailability() {
    override lateinit var type: String
}

class Unavailable() : ProductAvailability() {
    override lateinit var type: String
}

//interface ProductAvailability{
//
//        abstract val type: ProductAvailability.Type?
//
//    /**
//    *
//    * Values: available,unavailable
//    */
//    enum class Type(val value: kotlin.String) {
//
//        @JsonProperty("available") available("available"),
//
//        @JsonProperty("unavailable") unavailable("unavailable");
//
//    }
//
//}

