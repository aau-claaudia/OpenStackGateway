package dk.aau.claaudia.openstackgateway.models.requests

data class TempJobRequest(
    val base_image: String,
    val job_id: String,
    val machine_template: String,
    val owner_project: String,
    val owner_username: String,
    val request_parameters: RequestParameters,
    val total_grant_allocation: String
)

data class RequestParameters(
    val pubKey: PubKey
)

data class PubKey(
    val type: String,
    val value: String
)