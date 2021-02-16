package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param changeWorkingDirectory
 * @param runAsRoot
 * @param runAsRealUser
 */
data class DksducloudappstoreapiContainerDescription(

    @field:JsonProperty("changeWorkingDirectory") val changeWorkingDirectory: kotlin.Boolean? = null,

    @field:JsonProperty("runAsRoot") val runAsRoot: kotlin.Boolean? = null,

    @field:JsonProperty("runAsRealUser") val runAsRealUser: kotlin.Boolean? = null
) {

}

