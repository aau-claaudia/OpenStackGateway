package dk.aau.claaudia.openstackgateway.models.requests

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonUnwrapped
import kotlin.properties.Delegates

/*
This content of this file is copied from the ucloud sourcecode
 */

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type",
    defaultImpl = BulkRequest.Single::class
)
@JsonSubTypes(
    JsonSubTypes.Type(value = BulkRequest.Bulk::class, name = "bulk"),
)
sealed class BulkRequest<out T : Any> {
    abstract val items: List<T>

    class Single<T : Any> : BulkRequest<T>() {
        @get:JsonUnwrapped
        @set:JsonUnwrapped
        var item: T by Delegates.notNull()

        @get:JsonIgnore
        override val items
            get() = listOf(item)

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false
            other as BulkRequest<*>
            if (items != other.items) return false
            return true
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }

        override fun toString(): String = item.toString()
    }

    data class Bulk<T : Any>(override val items: List<T>) : BulkRequest<T>() {
        init {
            if (items.size > 1000) {
                throw IllegalArgumentException("Cannot exceed 1000 requests in a single payload")
            }
        }
    }
}

fun <T : Any> bulkRequestOf(vararg items: T): BulkRequest<T> {
    if (items.isEmpty()) error("No items provided")
    return if (items.size == 1) BulkRequest.Single<T>().apply { item = items.single() }
    else BulkRequest.Bulk(listOf(*items))
}

fun <T : Any> bulkRequestOf(items: Collection<T>): BulkRequest<T> {
    if (items.isEmpty()) error("No items provided")
    return if (items.size == 1) BulkRequest.Single<T>().apply { item = items.single() }
    else BulkRequest.Bulk(items.toList())
}


