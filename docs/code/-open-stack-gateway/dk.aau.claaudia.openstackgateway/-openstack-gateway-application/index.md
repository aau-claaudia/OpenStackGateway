//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway](../index.md)/[OpenstackGatewayApplication](index.md)

# OpenstackGatewayApplication

[jvm]\
@SpringBootApplication

@EnableConfigurationProperties(value = [[OpenStackProperties::class](../../dk.aau.claaudia.openstackgateway.config/-open-stack-properties/index.md), [UCloudProperties::class](../../dk.aau.claaudia.openstackgateway.config/-u-cloud-properties/index.md), [ProviderProperties::class](../../dk.aau.claaudia.openstackgateway.config/-provider-properties/index.md), [Messages::class](../../dk.aau.claaudia.openstackgateway.config/-messages/index.md)])

@ComponentScan(value = ["dk.aau.claaudia.openstackgateway", "dk.sdu.cloud.providers"])

class [OpenstackGatewayApplication](index.md)
