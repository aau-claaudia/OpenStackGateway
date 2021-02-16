package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param variableNames
 * @param prefixGlobal
 * @param suffixGlobal
 * @param prefixVariable
 * @param suffixVariable
 * @param isPrefixVariablePartOfArg
 * @param isSuffixVariablePartOfArg
 * @param type
 */
data class DksducloudappstoreapiVariableInvocationParameter(

    @field:JsonProperty("variableNames") val variableNames: kotlin.collections.List<kotlin.String>? = null,

    @field:JsonProperty("prefixGlobal") val prefixGlobal: kotlin.String? = null,

    @field:JsonProperty("suffixGlobal") val suffixGlobal: kotlin.String? = null,

    @field:JsonProperty("prefixVariable") val prefixVariable: kotlin.String? = null,

    @field:JsonProperty("suffixVariable") val suffixVariable: kotlin.String? = null,

    @field:JsonProperty("isPrefixVariablePartOfArg") val isPrefixVariablePartOfArg: kotlin.Boolean? = null,

    @field:JsonProperty("isSuffixVariablePartOfArg") val isSuffixVariablePartOfArg: kotlin.Boolean? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiVariableInvocationParameter.Type? = null
) {

    /**
     *
     * Values: `var`
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("var")
        `var`("var");

    }

}

