package dk.aau.claaudia.openstackgateway.services

import dk.aau.claaudia.openstackgateway.extensions.getLogger
import dk.sdu.cloud.calls.BulkRequest


import dk.sdu.cloud.models.DksducloudapporchestratorapiJobsControlChargeCreditsRequestItem
import dk.sdu.cloud.models.DksducloudapporchestratorapiJobsControlUpdateRequestItem
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono


typealias JobsStatusRequest = BulkRequest<DksducloudapporchestratorapiJobsControlUpdateRequestItem>
typealias JobsStatusResponse = Unit

@Service
class UCloudService(private val client: WebClient) {

    fun updateJobs(jobUpdateRequest: DksducloudapporchestratorapiJobsControlUpdateRequestItem){

        //Example request:
        val bab = DksducloudapporchestratorapiJobsControlUpdateRequestItem(
            "e7f8g94jdf8fjr84j3",
            DksducloudapporchestratorapiJobsControlUpdateRequestItem.State.rUNNING,
            "great"
        )

        return updateJobs(listOf(jobUpdateRequest))
    }

    fun updateJobs(jobUpdateRequests: List<DksducloudapporchestratorapiJobsControlUpdateRequestItem>) {
        logger.info("Updating jobs")

        val bulkRequest = bulkRequestOf(
            jobUpdateRequests
        )

        val response = client.post()
            .uri("update")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(BodyInserters.fromValue(bulkRequest))
            .retrieve().bodyToMono<Unit>().block() //FIXME replace unit with  actual response class here

        logger.info("response:", response)
        //return response
    }

    fun chargeCreditsJobs(jobChargeCreditsRequest: DksducloudapporchestratorapiJobsControlChargeCreditsRequestItem) {
        return chargeCreditsJobs(listOf(jobChargeCreditsRequest))
    }

    fun chargeCreditsJobs(jobChargeCreditsRequests: List<DksducloudapporchestratorapiJobsControlChargeCreditsRequestItem>) {
        logger.info("Charge credits jobs")

        val bulkRequest = bulkRequestOf(
            jobChargeCreditsRequests
        )

        val response = client.post()
            .uri("chargeCredits")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .body(BodyInserters.fromValue(bulkRequest))
            .retrieve().bodyToMono<Unit>().block() //FIXME replace unit with  actual response class here

        logger.info("response:", response)
        //return response
    }

    fun retrieveJobInformation(
        id: String?,
        includeApplication: Boolean?,
        includeParameters: Boolean?,
        includeProduct: Boolean?,
        includeUpdates: Boolean?)
    {
        client.get().uri(
            "retrieve",
            mutableMapOf(
                "id" to id,
                "includeApplication" to includeApplication,
                "includeParameters" to includeParameters,
                "includeProduct" to includeProduct,
                "includeUpdates" to includeUpdates,
            )
        ).retrieve()
    }

    fun submitFile(jobId: String, filePath: String){
        //TODO Implement this
        throw NotImplementedError()
    }

    companion object {
        val logger = getLogger()
    }
}