package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param why Human readable description of why the error occurred. This value is generally not stable.
 * @param errorCode Machine readable description of why the error occurred. This value is stable and can be relied upon.
 */
data class DksducloudCommonErrorMessageOpt(

    @field:JsonProperty("why") val why: kotlin.String? = null,

    @field:JsonProperty("errorCode") val errorCode: kotlin.String? = null
) {

}

