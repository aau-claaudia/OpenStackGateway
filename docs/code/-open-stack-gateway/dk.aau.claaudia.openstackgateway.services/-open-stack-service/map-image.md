//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.services](../index.md)/[OpenStackService](index.md)/[mapImage](map-image.md)

# mapImage

[jvm]\
fun [mapImage](map-image.md)(name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), version: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Given a ucloud application name and version find the corresponding openstack image Since ucloud uses name and version and not a unique name/id we use this config mapping instead of a strict naming convention of images The config is also used to control which images are available
