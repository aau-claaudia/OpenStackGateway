//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.services](../index.md)/[OpenStackService](index.md)/[createStacks](create-stacks.md)

# createStacks

[jvm]\
fun [createStacks](create-stacks.md)(jobs: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;Job&gt;)

Start the process of creating stacks from ucloud jobs For now, we use a single template, but this can change in the future. For each job start an asynchronous task
