//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.services](../index.md)/[OpenStackService](index.md)/[mapImage](map-image.md)

# mapImage

[jvm]\
fun [mapImage](map-image.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), version: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), toolImage: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)?): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Given a ucloud application name and version find the corresponding openstack image Since ucloud uses name and version and not a unique name/id we use this config mapping instead of a strict naming convention of images The config is also used to control which images are available

This above will be faced out when the a naming convention is implemented. New functionality All ucloud app names and versions can be mapped to a name of a openstack image using: ucloud-name-version Example: ucloudName: ubuntu, ucloudVersion: 20.04 will give the openstack name: &quot;ucloud-ubuntu-20.04&quot; We still need the id so get that from openstack
