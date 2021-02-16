package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * A reference to a license
 * @param id
 * @param type
 */
data class DksducloudappstoreapiAppParameterValueLicense(

    @field:JsonProperty("id") val id: kotlin.String? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiAppParameterValueLicense.Type? = null
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

