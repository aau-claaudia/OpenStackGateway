package dk.aau.claaudia.openstackgateway.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.task.TaskSchedulerBuilder
//import org.springframework.boot.task.ThreadPoolTaskSchedulerBuilder //TODO: use this from spring boot 3
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler


@Configuration
@EnableScheduling
class SchedulingConfig {

    // This is required because the default task scheduler conflicts with the one needed for websocket support
    @Qualifier("taskscheduler")
    @Bean
    //fun taskScheduler(builder: ThreadPoolTaskSchedulerBuilder): ThreadPoolTaskScheduler? { TODO: use this from spring boot 3
    fun taskScheduler(builder: TaskSchedulerBuilder): ThreadPoolTaskScheduler? {
        return builder.build()
    }

}