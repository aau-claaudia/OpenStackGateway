package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * HTTP Ingress
 * @param id
 * @param type
 */
data class DksducloudappstoreapiAppParameterValueIngress(

    @field:JsonProperty("id") val id: kotlin.String? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiAppParameterValueIngress.Type? = null
) {

    /**
     *
     * Values: ingress
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("ingress")
        ingress("ingress");

    }

}

