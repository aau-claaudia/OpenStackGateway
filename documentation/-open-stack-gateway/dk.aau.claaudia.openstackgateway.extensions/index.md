---
title: dk.aau.claaudia.openstackgateway.extensions
---
//[OpenStackGateway](../../index.html)/[dk.aau.claaudia.openstackgateway.extensions](index.html)



# Package dk.aau.claaudia.openstackgateway.extensions



## Types


| Name | Summary |
|---|---|
| [ExtensionConfig](-extension-config/index.html) | [jvm]<br>@Component<br>class [ExtensionConfig](-extension-config/index.html)(openStackProperties: [OpenStackProperties](../dk.aau.claaudia.openstackgateway.config/-open-stack-properties/index.html))<br>Allow extension functions to use config |


## Functions


| Name | Summary |
|---|---|
| [getLogger](get-logger.html) | [jvm]<br>inline fun [getLogger](get-logger.html)(): Logger |


## Properties


| Name | Summary |
|---|---|
| [openstackName](openstack-name.html) | [jvm]<br>val Job.[openstackName](openstack-name.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Given a ucloud job, add prefix to id to get openstack name |
| [ucloudId](ucloud-id.html) | [jvm]<br>val Stack.[ucloudId](ucloud-id.html): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Given a stack, remove the name prefix to get ucloud id |

