package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param password
 * @param port
 */
data class DksducloudappstoreapiVncDescriptionOpt(

    @field:JsonProperty("password") val password: kotlin.String? = null,

    @field:JsonProperty("port") val port: kotlin.Int? = null
) {

}

