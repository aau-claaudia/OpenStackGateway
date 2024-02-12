//[OpenStackGateway](../../../index.md)/[dk.aau.claaudia.openstackgateway.config](../index.md)/[SchedulingConfig](index.md)

# SchedulingConfig

[jvm]\
@Configuration

@EnableScheduling

class [SchedulingConfig](index.md)

## Constructors

| | |
|---|---|
| [SchedulingConfig](-scheduling-config.md) | [jvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [taskScheduler](task-scheduler.md) | [jvm]<br>@Qualifier(value = &quot;taskscheduler&quot;)<br>@Bean<br>fun [taskScheduler](task-scheduler.md)(builder: TaskSchedulerBuilder): ThreadPoolTaskScheduler? |
