---
title: OpenstackGatewayApplication
---
//[OpenStackGateway](../../../index.html)/[dk.aau.claaudia.openstackgateway](../index.html)/[OpenstackGatewayApplication](index.html)



# OpenstackGatewayApplication



[jvm]\
@SpringBootApplication



@EnableConfigurationProperties(value = [[OpenStackProperties::class](../../dk.aau.claaudia.openstackgateway.config/-open-stack-properties/index.html), [UCloudProperties::class](../../dk.aau.claaudia.openstackgateway.config/-u-cloud-properties/index.html), [ProviderProperties::class](../../dk.aau.claaudia.openstackgateway.config/-provider-properties/index.html), [Messages::class](../../dk.aau.claaudia.openstackgateway.config/-messages/index.html)])



@ComponentScan(value = ["dk.aau.claaudia.openstackgateway", "dk.sdu.cloud.providers"])



class [OpenstackGatewayApplication](index.html)


