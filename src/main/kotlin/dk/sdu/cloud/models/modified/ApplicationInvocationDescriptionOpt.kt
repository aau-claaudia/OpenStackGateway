package dk.sdu.cloud.models.modified

import com.fasterxml.jackson.annotation.JsonProperty
import dk.sdu.cloud.models.*
import javax.validation.Valid

/**
 *
 * @param tool
 * @param invocation
 * @param parameters
 * @param outputFileGlobs
 * @param applicationType
 * @param vnc
 * @param web
 * @param container
 * @param environment
 * @param allowAdditionalMounts
 * @param allowAdditionalPeers
 * @param allowMultiNode
 * @param fileExtensions
 * @param licenseServers
 * @param shouldAllowAdditionalMounts
 * @param shouldAllowAdditionalPeers
 */
data class ApplicationInvocationDescriptionOpt(

    @field:Valid
    @field:JsonProperty("tool") val tool: DksducloudappstoreapiToolReference? = null,

    @field:Valid
    @field:JsonProperty("invocation") val invocation: kotlin.collections.List<ApplicationInvocationDescriptionInvocation>? = null,

    @field:Valid
    @field:JsonProperty("parameters") val parameters: kotlin.collections.List<ApplicationParameter>? = null,

    @field:JsonProperty("outputFileGlobs") val outputFileGlobs: kotlin.collections.List<kotlin.String>? = null,

    @field:JsonProperty("applicationType") val applicationType: ApplicationType? = null,

    @field:Valid
    @field:JsonProperty("vnc") val vnc: DksducloudappstoreapiVncDescriptionOpt? = null,

    @field:Valid
    @field:JsonProperty("web") val web: DksducloudappstoreapiWebDescriptionOpt? = null,

    @field:Valid
    @field:JsonProperty("container") val container: DksducloudappstoreapiContainerDescriptionOpt? = null,

    @field:Valid
    @field:JsonProperty("environment") val environment: kotlin.collections.Map<kotlin.String, DksducloudappstoreapiInvocationParameter>? = null,

    @field:JsonProperty("allowAdditionalMounts") val allowAdditionalMounts: kotlin.Boolean? = null,

    @field:JsonProperty("allowAdditionalPeers") val allowAdditionalPeers: kotlin.Boolean? = null,

    @field:JsonProperty("allowMultiNode") val allowMultiNode: kotlin.Boolean? = null,

    @field:JsonProperty("fileExtensions") val fileExtensions: kotlin.collections.List<kotlin.String>? = null,

    @field:JsonProperty("licenseServers") val licenseServers: kotlin.collections.List<kotlin.String>? = null,

    @field:JsonProperty("shouldAllowAdditionalMounts") val shouldAllowAdditionalMounts: kotlin.Boolean? = null,

    @field:JsonProperty("shouldAllowAdditionalPeers") val shouldAllowAdditionalPeers: kotlin.Boolean? = null
) {

    /**
     *
     * Values: bATCH,vNC,wEB
     */
    enum class ApplicationType(val value: kotlin.String) {

        @JsonProperty("BATCH")
        bATCH("BATCH"),

        @JsonProperty("VNC")
        vNC("VNC"),

        @JsonProperty("WEB")
        wEB("WEB");

    }

}

