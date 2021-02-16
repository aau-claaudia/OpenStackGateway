package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 *
 * @param type
 * @param defaultValue
 * @param description
 * @param name
 * @param optional
 * @param title
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes(
    JsonSubTypes.Type(value = DksducloudappstoreapiApplicationParameterBool::class, name = "boolean"),
    JsonSubTypes.Type(value = DksducloudappstoreapiApplicationParameterEnumeration::class, name = "enumeration"),
    JsonSubTypes.Type(value = DksducloudappstoreapiApplicationParameterFloatingPoint::class, name = "floating_point"),
    JsonSubTypes.Type(value = DksducloudappstoreapiApplicationParameterInputDirectory::class, name = "input_directory"),
    JsonSubTypes.Type(value = DksducloudappstoreapiApplicationParameterInputFile::class, name = "input_file"),
    JsonSubTypes.Type(value = DksducloudappstoreapiApplicationParameterInteger::class, name = "integer"),
    JsonSubTypes.Type(value = DksducloudappstoreapiApplicationParameterLicenseServer::class, name = "license_server"),
    JsonSubTypes.Type(value = DksducloudappstoreapiApplicationParameterPeer::class, name = "peer"),
    JsonSubTypes.Type(value = DksducloudappstoreapiApplicationParameterText::class, name = "text")
)

interface DksducloudappstoreapiApplicationParameterOpt {

    val type: DksducloudappstoreapiApplicationParameterOpt.Type?

    val defaultValue: kotlin.Any?

    val description: kotlin.String?

    val name: kotlin.String?

    val optional: kotlin.Boolean?

    val title: kotlin.String?

    /**
     *
     * Values: inputFile,inputDirectory,text,integer,boolean,floatingPoint,peer,enumeration,licenseServer
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("input_file")
        inputFile("input_file"),

        @JsonProperty("input_directory")
        inputDirectory("input_directory"),

        @JsonProperty("text")
        text("text"),

        @JsonProperty("integer")
        integer("integer"),

        @JsonProperty("boolean")
        boolean("boolean"),

        @JsonProperty("floating_point")
        floatingPoint("floating_point"),

        @JsonProperty("peer")
        peer("peer"),

        @JsonProperty("enumeration")
        enumeration("enumeration"),

        @JsonProperty("license_server")
        licenseServer("license_server");

    }

}

