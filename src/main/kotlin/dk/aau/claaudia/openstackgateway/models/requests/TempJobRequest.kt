package dk.aau.claaudia.openstackgateway.models.requests

data class TempJobRequest(
    val base_image: String,
    val machine_template: MachineTemplate,
    val owner_project: String,
    val owner_username: String,
    val request: String,
    val request_parameters: RequestParameters,
    val total_grant_allocation: String
)

data class MachineTemplate(
    val availability: Availability,
    val balance: Any,
    val category: Category,
    val cpu: Int,
    val description: String,
    val gpu: Any,
    val hiddenInGrantApplications: Boolean,
    val id: String,
    val memoryInGigs: Any,
    val pricePerUnit: Int,
    val priority: Int,
    val type: String
)

data class RequestParameters(
    val pubKey: PubKey
)

data class Availability(
    val type: String
)

data class Category(
    val id: String,
    val provider: String
)

data class PubKey(
    val type: String,
    val value: String
)