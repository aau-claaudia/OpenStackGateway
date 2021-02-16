package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

/**
 *
 * @param jobId
 * @param rank
 * @param type
 */

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes(
    JsonSubTypes.Type(value = DksducloudapporchestratorapiOpenSessionShell::class, name = "shell"),
    JsonSubTypes.Type(value = DksducloudapporchestratorapiOpenSessionVnc::class, name = "vnc"),
    JsonSubTypes.Type(value = DksducloudapporchestratorapiOpenSessionWeb::class, name = "web")
)

interface DksducloudapporchestratorapiOpenSessionOpt {

    val jobId: kotlin.String?

    val rank: kotlin.Int?

    val type: DksducloudapporchestratorapiOpenSessionOpt.Type?

    /**
     *
     * Values: shell,web,vnc
     */
    enum class Type(val value: kotlin.String) {

        @JsonProperty("shell")
        shell("shell"),

        @JsonProperty("web")
        web("web"),

        @JsonProperty("vnc")
        vnc("vnc");

    }

}

