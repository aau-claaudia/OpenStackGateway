package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.Valid

/**
 *
 * @param info
 * @param container
 * @param defaultNumberOfNodes
 * @param defaultTimeAllocation
 * @param requiredModules
 * @param authors
 * @param title
 * @param description
 * @param backend
 * @param license
 */
data class DksducloudappstoreapiNormalizedToolDescription(

    @field:Valid
    @field:JsonProperty("info") val info: DksducloudappstoreapiNameAndVersion? = null,

    @field:JsonProperty("container") val container: kotlin.String? = null,

    @field:JsonProperty("defaultNumberOfNodes") val defaultNumberOfNodes: kotlin.Int? = null,

    @field:Valid
    @field:JsonProperty("defaultTimeAllocation") val defaultTimeAllocation: DksducloudappstoreapiSimpleDuration? = null,

    @field:JsonProperty("requiredModules") val requiredModules: kotlin.collections.List<kotlin.String>? = null,

    @field:JsonProperty("authors") val authors: kotlin.collections.List<kotlin.String>? = null,

    @field:JsonProperty("title") val title: kotlin.String? = null,

    @field:JsonProperty("description") val description: kotlin.String? = null,

    @field:JsonProperty("backend") val backend: DksducloudappstoreapiNormalizedToolDescription.Backend? = null,

    @field:JsonProperty("license") val license: kotlin.String? = null
) {

    /**
     *
     * Values: sINGULARITY,dOCKER
     */
    enum class Backend(val value: kotlin.String) {

        @JsonProperty("SINGULARITY")
        sINGULARITY("SINGULARITY"),

        @JsonProperty("DOCKER")
        dOCKER("DOCKER");

    }

}

