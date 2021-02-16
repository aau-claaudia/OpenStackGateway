package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param word
 * @param type
 */
data class DksducloudappstoreapiWordInvocationParameterOpt(

    @field:JsonProperty("word") val word: kotlin.String? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiWordInvocationParameterOpt.Type? = null
) {

    /**
     *
     * Values: word
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("word")
        word("word");

    }

}

