package com.linkapital.core.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
public class ThreadPoolTaskConfig {

    @Value("${thread.pool.size}")
    private int poolSize;
    @Value("${thread.pool.max-pool-size}")
    private int maxPoolSize;
    @Value("${thread.queue.capacity}")
    private int queueCapacity;
    @Value("${thread.prefix}")
    private String prefix;

    /**
     * Define a custom {@link Executor} bean, to take control of the process and not depend of the Spring Boot Executor
     * auto configuration
     *
     * @return {@link Executor} an executor bean
     */
    @Bean
    public Executor threadPoolTaskExecutor() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(prefix);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();

        return executor;
    }

}
