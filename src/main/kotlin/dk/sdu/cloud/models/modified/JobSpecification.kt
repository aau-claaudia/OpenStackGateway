package dk.sdu.cloud.models.modified

import com.fasterxml.jackson.annotation.JsonProperty
import dk.sdu.cloud.models.DksducloudaccountingapiProductReference
import dk.sdu.cloud.models.DksducloudappstoreapiApplicationOpt
import dk.sdu.cloud.models.DksducloudappstoreapiNameAndVersion
import dk.sdu.cloud.models.DksducloudappstoreapiSimpleDurationOpt
import javax.validation.Valid

/**
 *
 * @param application
 * @param product
 * @param name A name for this job assigned by the user.  The name can help a user identify why and with which parameters a job was started. This value is suitable for display in user interfaces.
 * @param replicas The number of replicas to start this job in  The `resources` supplied will be mounted in every replica. Some `resources` might only be supported in an 'exclusive use' mode. This will cause the job to fail if `replicas != 1`.
 * @param allowDuplicateJob Allows the job to be started even when a job is running in an identical configuration  By default, UCloud will prevent you from accidentally starting two jobs with identical configuration. This field must be set to `true` to allow you to create two jobs with identical configuration.
 * @param parameters Parameters which are consumed by the job  The available parameters are defined by the `application`. This attribute is not included by default unless `includeParameters` is specified.
 * @param resources Additional resources which are made available into the job  This attribute is not included by default unless `includeParameters` is specified. Note: Not all resources can be attached to a job. UCloud supports the following parameter types as resources:   - `file`  - `peer`  - `network`  - `block_storage`  - `ingress`
 * @param timeAllocation
 * @param resolvedProduct
 * @param resolvedApplication
 */
data class JobSpecification(

    @field:Valid
    @field:JsonProperty("application") val application: DksducloudappstoreapiNameAndVersion? = null,

    @field:Valid
    @field:JsonProperty("product") val product: DksducloudaccountingapiProductReference? = null,

    @field:JsonProperty("name") val name: kotlin.String? = null,

    @field:JsonProperty("replicas") val replicas: kotlin.Int? = null,

    @field:JsonProperty("allowDuplicateJob") val allowDuplicateJob: kotlin.Boolean? = null,

    @field:Valid
    @field:JsonProperty("parameters") val parameters: kotlin.collections.Map<kotlin.String, AppParameterValue>? = null,

    @field:Valid
    @field:JsonProperty("resources") val resources: kotlin.collections.List<AppParameterValue>? = null,

    @field:Valid
    @field:JsonProperty("timeAllocation") val timeAllocation: DksducloudappstoreapiSimpleDurationOpt? = null,

    @field:Valid
    @field:JsonProperty("resolvedProduct") val resolvedProduct: ProductComputeOpt? = null,

    @field:Valid
    @field:JsonProperty("resolvedApplication") val resolvedApplication: DksducloudappstoreapiApplicationOpt? = null
) {

}

