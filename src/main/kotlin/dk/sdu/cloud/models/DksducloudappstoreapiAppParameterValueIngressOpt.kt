package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param id
 * @param type
 */
data class DksducloudappstoreapiAppParameterValueIngressOpt(

    @field:JsonProperty("id") val id: kotlin.String? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiAppParameterValueIngressOpt.Type? = null
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

