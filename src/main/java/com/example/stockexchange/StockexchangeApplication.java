package com.example.stockexchange;

import java.util.concurrent.Executor;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@SpringBootApplication
@EnableAutoConfiguration
public class StockexchangeApplication extends AsyncConfigurerSupport {

	private static final Logger LOG = Logger.getLogger(StockexchangeApplication.class);
	
	/*
	 * The start of StockExchange application
	 */
	public static void main(String[] args) {
		SpringApplication.run(StockexchangeApplication.class, args);
		LOG.info("..StockexchangeApplication Started..");
	}

	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(2);
		executor.setMaxPoolSize(2);
		executor.setQueueCapacity(100);
		executor.setThreadNamePrefix("StockExchangerThread-");
		executor.initialize();
		return executor;
	}
}
