package com.SmsCallback.Config;

import javax.annotation.PreDestroy;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.stereotype.Component;


@Configuration
public class QueueProducerConfiguration {

	private final CachingConnectionFactory connectionFactory;

	public QueueProducerConfiguration(ConnectionFactory connectionFactory) {
		if (connectionFactory instanceof CachingConnectionFactory) {
			this.connectionFactory = (CachingConnectionFactory) connectionFactory;
		} else {
			throw new IllegalStateException("Expected a CachingConnectionFactory");
		}
	}

	@Bean
	RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {

		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
		return rabbitTemplate;
	}

	@PreDestroy
	public void cleanup() {
		// Close RabbitMQ resources
		if (connectionFactory != null) {
			connectionFactory.destroy();
		}
	}

}
