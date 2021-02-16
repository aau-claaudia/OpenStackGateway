package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param timestamp
 * @param state
 * @param status
 */
data class DksducloudapporchestratorapiJobUpdateOpt(

    @field:JsonProperty("timestamp") val timestamp: kotlin.Long? = null,

    @field:JsonProperty("state") val state: DksducloudapporchestratorapiJobUpdateOpt.State? = null,

    @field:JsonProperty("status") val status: kotlin.String? = null
) {

    /**
     *
     * Values: iNQUEUE,rUNNING,cANCELING,sUCCESS,fAILURE,eXPIRED
     */
    enum class State(val value: kotlin.String) {

        @JsonProperty("IN_QUEUE")
        iNQUEUE("IN_QUEUE"),

        @JsonProperty("RUNNING")
        rUNNING("RUNNING"),

        @JsonProperty("CANCELING")
        cANCELING("CANCELING"),

        @JsonProperty("SUCCESS")
        sUCCESS("SUCCESS"),

        @JsonProperty("FAILURE")
        fAILURE("FAILURE"),

        @JsonProperty("EXPIRED")
        eXPIRED("EXPIRED");

    }

}

