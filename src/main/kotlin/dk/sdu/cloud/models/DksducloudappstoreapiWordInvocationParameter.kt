package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param word
 * @param type
 */
data class DksducloudappstoreapiWordInvocationParameter(

    @field:JsonProperty("word") val word: kotlin.String? = null,

    @field:JsonProperty("type") val type: DksducloudappstoreapiWordInvocationParameter.Type? = null
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

