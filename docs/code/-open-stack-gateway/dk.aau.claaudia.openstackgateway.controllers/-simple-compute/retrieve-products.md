//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.controllers](../index.md)/[SimpleCompute](index.md)/[retrieveProducts](retrieve-products.md)

# retrieveProducts

[jvm]\
open override fun [retrieveProducts](retrieve-products.md)(request: [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)): BulkResponse&lt;ComputeSupport&gt;

Provide ucloud with the available products. These are basically equivalent to openstack flavors but need to adhere to the ucloud format This includes information additional information, e.g., product is Virtual Machine The UCloud category is translated to availability_zone on the flavor in openstack. All information about ComputeSupport is also hardcoded for now
