package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param hours
 * @param minutes
 * @param seconds
 */
data class DksducloudappstoreapiSimpleDurationOpt(

    @field:JsonProperty("hours") val hours: kotlin.Int? = null,

    @field:JsonProperty("minutes") val minutes: kotlin.Int? = null,

    @field:JsonProperty("seconds") val seconds: kotlin.Int? = null
) {

}

