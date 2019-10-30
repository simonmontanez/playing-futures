package com.company;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@EnableScheduling
@Configuration
public class ThreadsConfig {

    @Bean(name = "ThreadPool-ServiceDiscount")
    public ThreadPoolTaskExecutor serviceDiscountExecutor() {
        return createExecutor();
    }

    @Bean(name = "ThreadPool-ServiceTaxes")
    public ThreadPoolTaskExecutor serviceTaxesExecutor() {
        return createExecutor();
    }

    private ThreadPoolTaskExecutor createExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(2);
        threadPoolTaskExecutor.setMaxPoolSize(4);
        return threadPoolTaskExecutor;
    }
}
