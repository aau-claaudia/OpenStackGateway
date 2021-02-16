//package dk.sdu.cloud.models
//
//import java.util.Objects
//import com.fasterxml.jackson.annotation.JsonProperty
//import com.fasterxml.jackson.annotation.JsonSubTypes
//import com.fasterxml.jackson.annotation.JsonTypeInfo
//import com.fasterxml.jackson.annotation.JsonValue
//import javax.validation.constraints.DecimalMax
//import javax.validation.constraints.DecimalMin
//import javax.validation.constraints.Max
//import javax.validation.constraints.Min
//import javax.validation.constraints.NotNull
//import javax.validation.constraints.Pattern
//import javax.validation.constraints.Size
//import javax.validation.Valid
//
///**
// * A parameter supplied to a compute job
// * @param type
// */
//
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
//@JsonSubTypes(
//      JsonSubTypes.Type(value = DksducloudappstoreapiAppParameterValueBlockStorage::class, name = "block_storage"),
//      JsonSubTypes.Type(value = DksducloudappstoreapiAppParameterValueBool::class, name = "boolean"),
//      JsonSubTypes.Type(value = DksducloudappstoreapiAppParameterValueFile::class, name = "file"),
//      JsonSubTypes.Type(value = DksducloudappstoreapiAppParameterValueFloatingPoint::class, name = "floating_point"),
//      JsonSubTypes.Type(value = DksducloudappstoreapiAppParameterValueIngress::class, name = "ingress"),
//      JsonSubTypes.Type(value = DksducloudappstoreapiAppParameterValueInteger::class, name = "integer"),
//      JsonSubTypes.Type(value = DksducloudappstoreapiAppParameterValueLicense::class, name = "license_server"),
//      JsonSubTypes.Type(value = DksducloudappstoreapiAppParameterValueNetwork::class, name = "network"),
//      JsonSubTypes.Type(value = DksducloudappstoreapiAppParameterValuePeer::class, name = "peer"),
//      JsonSubTypes.Type(value = DksducloudappstoreapiAppParameterValueText::class, name = "text")
//)
//interface DksducloudappstoreapiAppParameterValue{
//
//        val type: DksducloudappstoreapiAppParameterValue.Type?
//
//    /**
//    *
//    * Values: file,boolean,text,integer,floatingPoint,peer,licenseServer,network,blockStorage,ingress
//    */
//    enum class Type(val value: kotlin.String) {
//        @JsonProperty("file") file("file"),
//        @JsonProperty("boolean") boolean("boolean"),
//        @JsonProperty("text") text("text"),
//        @JsonProperty("integer") integer("integer"),
//        @JsonProperty("floating_point") floatingPoint("floating_point"),
//        @JsonProperty("peer") peer("peer"),
//        @JsonProperty("license_server") licenseServer("license_server"),
//        @JsonProperty("network") network("network"),
//        @JsonProperty("block_storage") blockStorage("block_storage"),
//        @JsonProperty("ingress") ingress("ingress");
//    }
//
//}

package dk.sdu.cloud.models.modified


//import dk.sdu.cloud.calls.RPCException

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal
import java.math.BigInteger

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "type",
    visible = true
)
@JsonSubTypes(
    JsonSubTypes.Type(value = File::class, name = "file"),
    JsonSubTypes.Type(value = Bool::class, name = "boolean"),
    JsonSubTypes.Type(value = Text::class, name = "text"),
    JsonSubTypes.Type(value = Integer::class, name = "integer"),
    JsonSubTypes.Type(value = FloatingPoint::class, name = "floating_point"),
    JsonSubTypes.Type(value = Peer::class, name = "peer"),
    JsonSubTypes.Type(value = License::class, name = "license_server"),
    JsonSubTypes.Type(value = Network::class, name = "network"),
    JsonSubTypes.Type(value = BlockStorage::class, name = "block_storage"),
    JsonSubTypes.Type(value = Ingress::class, name = "ingress"),
)

// Trying to use the fix here: https://stackoverflow.com/questions/64724130/kotlin-and-jackson-missing-type-id-when-trying-to-resolve-subtype-of-simple-ty

sealed class AppParameterValue {
    abstract val type: String
}

data class File(val path: String, var readOnly: Boolean = false) : AppParameterValue() {
    override lateinit var type: String
}

data class Bool(val value: Boolean) : AppParameterValue() {
    override lateinit var type: String
}

data class Text(val value: String) : AppParameterValue() {
    override lateinit var type: String
}

data class Integer(val value: BigInteger) : AppParameterValue() {
    override lateinit var type: String
}

data class FloatingPoint(val value: BigDecimal) : AppParameterValue() {
    override lateinit var type: String
}

data class Peer(val hostname: String, val jobId: String) : AppParameterValue() {
    init {
        if (hostname == null || !hostname.matches(hostNameRegex)) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid hostname: $hostname")
        }
    }

    override lateinit var type: String
}

data class License(val id: String) : AppParameterValue() {
    override lateinit var type: String
}

data class BlockStorage(val id: String) : AppParameterValue() {
    override lateinit var type: String
}

data class Network(val id: String) : AppParameterValue() {
    override lateinit var type: String
}

data class Ingress(val id: String) : AppParameterValue() {
    override lateinit var type: String
}

//sealed class AppParameterValue {

//

//}

private val hostNameRegex =
    Regex(
        "^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\-]*[a-zA-Z0-9])\\.)*" +
            "([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\-]*[A-Za-z0-9])\$"
    )


