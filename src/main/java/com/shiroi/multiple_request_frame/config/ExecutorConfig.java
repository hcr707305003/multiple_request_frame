package com.shiroi.multiple_request_frame.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ExecutorConfig {
    /** 核心线程数(默认线程数) */
    private int corePoolSize = 10;
    /** 最大线程数 */
    private int maxPoolSize = 20;
    /** 允许线程空闲时间（单位：默认为秒） */
    private static final int keepAliveTime = 60;
    /** 缓冲队列大小 */
    private int queueCapacity = 10;

    @Bean
    public Executor asyncServiceExecutor(){
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(corePoolSize);
        //配置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //配置空闲时间
        executor.setKeepAliveSeconds(keepAliveTime);
        //配置队列大小
        executor.setQueueCapacity(queueCapacity);
        //配置线程前缀名
        executor.setThreadNamePrefix("async-service-");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        //执行初始化
        executor.initialize();
        return executor;
    }
}
