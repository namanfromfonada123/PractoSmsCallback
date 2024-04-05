package com.SmsCallback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@SpringBootApplication
@EnableScheduling
@EnableAsync
public class SmsCallbackRecordApplication{

	public static void main(String[] args)  {
		SpringApplication.run(SmsCallbackRecordApplication.class, args);
	}

	@Bean("Async")
	 TaskExecutor getAsyncTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(40);
		executor.setQueueCapacity(30);
		executor.setWaitForTasksToCompleteOnShutdown(false);
		executor.setThreadNamePrefix("Async- ");
		return executor;
	}

}