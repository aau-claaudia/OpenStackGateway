package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param variable
 * @param type
 */
data class DksducloudappstoreapiEnvironmentVariableParameter(

    @field:JsonProperty("variable") val variable: kotlin.String? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiEnvironmentVariableParameter.Type? = null
) {

    /**
     *
     * Values: env
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("env")
        env("env");

    }

}

