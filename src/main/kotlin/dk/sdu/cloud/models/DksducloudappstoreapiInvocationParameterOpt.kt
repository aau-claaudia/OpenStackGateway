package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 *
 * @param type
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes(
    JsonSubTypes.Type(value = DksducloudappstoreapiBooleanFlagParameter::class, name = "bool_flag"),
    JsonSubTypes.Type(value = DksducloudappstoreapiEnvironmentVariableParameter::class, name = "env"),
    JsonSubTypes.Type(value = DksducloudappstoreapiVariableInvocationParameter::class, name = "var"),
    JsonSubTypes.Type(value = DksducloudappstoreapiWordInvocationParameter::class, name = "word")
)

interface DksducloudappstoreapiInvocationParameterOpt {

    val type: DksducloudappstoreapiInvocationParameterOpt.Type?

    /**
     *
     * Values: word,boolFlag,`var`,env
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("word")
        word("word"),

        @JsonProperty("bool_flag")
        boolFlag("bool_flag"),

        @JsonProperty("var")
        `var`("var"),

        @JsonProperty("env")
        env("env");

    }

}

