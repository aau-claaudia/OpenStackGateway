package dk.sdu.cloud.models

import com.fasterxml.jackson.annotation.JsonProperty

/**
 *
 * @param state The current of state of the `Job`.  This will match the latest state set in the `updates`
 * @param startedAt Timestamp matching when the `Job` most recently transitioned to the `RUNNING` state.  For `Job`s which suspend this might occur multiple times. This will always point to the latest pointin time it started running.
 * @param expiresAt Timestamp matching when the `Job` is set to expire.  This is generally equal to `startedAt + timeAllocation`. Note that this field might be `null` if the `Job` has no associated deadline. For `Job`s that suspend however, this is more likely to beequal to the initial `RUNNING` state + `timeAllocation`.
 */
data class DksducloudapporchestratorapiJobStatusOpt(

    @field:JsonProperty("state") val state: DksducloudapporchestratorapiJobStatusOpt.State? = null,

    @field:JsonProperty("startedAt") val startedAt: kotlin.Long? = null,

    @field:JsonProperty("expiresAt") val expiresAt: kotlin.Long? = null
) {

    /**
     * The current of state of the `Job`.  This will match the latest state set in the `updates`
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

