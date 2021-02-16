package dk.sdu.cloud.models.modified

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 *
 * @param type
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes(
    JsonSubTypes.Type(value = BooleanFlagParameter::class, name = "bool_flag"),
    JsonSubTypes.Type(value = EnvironmentVariableParameter::class, name = "env"),
    JsonSubTypes.Type(value = VariableInvocationParameter::class, name = "var"),
    JsonSubTypes.Type(value = WordInvocationParameter::class, name = "word")
)

sealed class ApplicationInvocationDescriptionInvocation {
    abstract val type: String
}

data class BooleanFlagParameter(
    @field:JsonProperty("variableName") val variableName: kotlin.String? = null,
    @field:JsonProperty("flag") val flag: kotlin.String? = null,
) : ApplicationInvocationDescriptionInvocation() {
    @field:JsonProperty("type")
    override lateinit var type: String
}

data class EnvironmentVariableParameter(
    @field:JsonProperty("variable") val variable: kotlin.String? = null
) : ApplicationInvocationDescriptionInvocation() {
    @field:JsonProperty("type")
    override lateinit var type: String
}

data class VariableInvocationParameter(
    @field:JsonProperty("variableNames") val variableNames: kotlin.collections.List<kotlin.String>? = null,
    @field:JsonProperty("prefixGlobal") val prefixGlobal: kotlin.String? = null,
    @field:JsonProperty("suffixGlobal") val suffixGlobal: kotlin.String? = null,
    @field:JsonProperty("prefixVariable") val prefixVariable: kotlin.String? = null,
    @field:JsonProperty("suffixVariable") val suffixVariable: kotlin.String? = null,
    @field:JsonProperty("isPrefixVariablePartOfArg") val isPrefixVariablePartOfArg: kotlin.Boolean? = null,
    @field:JsonProperty("isSuffixVariablePartOfArg") val isSuffixVariablePartOfArg: kotlin.Boolean? = null
) : ApplicationInvocationDescriptionInvocation() {
    @field:JsonProperty("type")
    override lateinit var type: String
}

data class WordInvocationParameter(val word: String? = null) : ApplicationInvocationDescriptionInvocation() {
    @field:JsonProperty("type")
    override lateinit var type: String
}