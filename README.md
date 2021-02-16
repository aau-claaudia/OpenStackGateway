# OpenStackGateway

Compute provider API for UCloud

### PRELIMINARY UCloud Provider API Spec:
This is outdated and subject to change!

[Provider API](specs/ProviderAPI.html)

# Model generation.

`./gradlew openApiGenerate`

can be used to generate models
Not everything works however. I needed to use the bulkrequest model from ucloud as well as some modifications to them.
This will need to be updated in the future.
It uses the spec definition from the specs folder.
