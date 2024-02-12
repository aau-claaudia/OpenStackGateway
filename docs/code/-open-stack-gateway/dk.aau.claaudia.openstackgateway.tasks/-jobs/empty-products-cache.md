//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.tasks](../index.md)/[Jobs](index.md)/[emptyProductsCache](empty-products-cache.md)

# emptyProductsCache

[jvm]\

@CacheEvict(value = [&quot;products&quot;], allEntries = true)

@Scheduled(cron = &quot;0 0 3 * * *&quot;)

fun [emptyProductsCache](empty-products-cache.md)()
