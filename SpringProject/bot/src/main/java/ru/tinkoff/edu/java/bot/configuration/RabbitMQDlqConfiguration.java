package ru.tinkoff.edu.java.bot.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQDlqConfiguration {
    private final ApplicationConfig applicationConfig;

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder
            .durable(applicationConfig.scrapperQueue().queueName() + ".dlq")
            .build();
    }

//    @Bean
//    public DirectExchange directExchangeDlq() {
//        return new DirectExchange(applicationConfig.scrapperQueue().exchangeName(), true, false);
//    }

    @Bean
    public Binding deadLetterQueueBinding(@Qualifier("deadLetterQueue") Queue dlq, DirectExchange directExchange) {
        return BindingBuilder
            .bind(dlq)
            .to(directExchange)
            .with(applicationConfig.scrapperQueue().routingKey() + ".dlq");
    }
}
