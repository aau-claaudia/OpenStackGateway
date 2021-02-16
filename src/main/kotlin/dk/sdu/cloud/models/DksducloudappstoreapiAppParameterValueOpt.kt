package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 *
 * @param type
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes(
    JsonSubTypes.Type(value = DksducloudappstoreapiAppParameterValueBlockStorage::class, name = "block_storage"),
    JsonSubTypes.Type(value = DksducloudappstoreapiAppParameterValueBool::class, name = "boolean"),
    JsonSubTypes.Type(value = DksducloudappstoreapiAppParameterValueFile::class, name = "file"),
    JsonSubTypes.Type(value = DksducloudappstoreapiAppParameterValueFloatingPoint::class, name = "floating_point"),
    JsonSubTypes.Type(value = DksducloudappstoreapiAppParameterValueIngress::class, name = "ingress"),
    JsonSubTypes.Type(value = DksducloudappstoreapiAppParameterValueInteger::class, name = "integer"),
    JsonSubTypes.Type(value = DksducloudappstoreapiAppParameterValueLicense::class, name = "license_server"),
    JsonSubTypes.Type(value = DksducloudappstoreapiAppParameterValueNetwork::class, name = "network"),
    JsonSubTypes.Type(value = DksducloudappstoreapiAppParameterValuePeer::class, name = "peer"),
    JsonSubTypes.Type(value = DksducloudappstoreapiAppParameterValueText::class, name = "text")
)

interface DksducloudappstoreapiAppParameterValueOpt {

    val type: DksducloudappstoreapiAppParameterValueOpt.Type?

    /**
     *
     * Values: file,boolean,text,integer,floatingPoint,peer,licenseServer,network,blockStorage,ingress
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("file")
        file("file"),

        @JsonProperty("boolean")
        boolean("boolean"),

        @JsonProperty("text")
        text("text"),

        @JsonProperty("integer")
        integer("integer"),

        @JsonProperty("floating_point")
        floatingPoint("floating_point"),

        @JsonProperty("peer")
        peer("peer"),

        @JsonProperty("license_server")
        licenseServer("license_server"),

        @JsonProperty("network")
        network("network"),

        @JsonProperty("block_storage")
        blockStorage("block_storage"),

        @JsonProperty("ingress")
        ingress("ingress");

    }

}

