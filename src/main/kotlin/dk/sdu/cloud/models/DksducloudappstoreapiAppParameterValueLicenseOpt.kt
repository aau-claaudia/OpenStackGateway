package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param id
 * @param type
 */
data class DksducloudappstoreapiAppParameterValueLicenseOpt(

    @field:JsonProperty("id") val id: kotlin.String? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiAppParameterValueLicenseOpt.Type? = null
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

