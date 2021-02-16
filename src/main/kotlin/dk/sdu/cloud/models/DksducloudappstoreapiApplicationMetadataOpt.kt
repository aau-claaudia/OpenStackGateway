package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param name
 * @param version
 * @param authors
 * @param title
 * @param description
 * @param website
 * @param public
 */
data class DksducloudappstoreapiApplicationMetadataOpt(

    @field:JsonProperty("name") val name: kotlin.String? = null,

    @field:JsonProperty("version") val version: kotlin.String? = null,

    @field:JsonProperty("authors") val authors: kotlin.collections.List<kotlin.String>? = null,

    @field:JsonProperty("title") val title: kotlin.String? = null,

    @field:JsonProperty("description") val description: kotlin.String? = null,

    @field:JsonProperty("website") val website: kotlin.String? = null,

    @field:JsonProperty("public") val public: kotlin.Boolean? = null
) {

}

