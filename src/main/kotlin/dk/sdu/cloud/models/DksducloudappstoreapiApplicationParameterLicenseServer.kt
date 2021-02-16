package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 *
 * @param name
 * @param title
 * @param optional
 * @param description
 * @param tagged
 * @param defaultValue
 * @param type
 */
data class DksducloudappstoreapiApplicationParameterLicenseServer(

    @field:JsonProperty("name") val name: kotlin.String? = null,

    @field:JsonProperty("title") val title: kotlin.String? = null,

    @field:JsonProperty("optional") val optional: kotlin.Boolean? = null,

    @field:JsonProperty("description") val description: kotlin.String? = null,

    @field:JsonProperty("tagged") val tagged: kotlin.collections.List<kotlin.String>? = null,

    @field:Valid
    @field:JsonProperty("defaultValue") val defaultValue: kotlin.Any? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiApplicationParameterLicenseServer.Type? = null
) {

    /**
     *
     * Values: licenseServer
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("license_server")
        licenseServer("license_server");

    }

}

