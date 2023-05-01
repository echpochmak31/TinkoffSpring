package ru.tinkoff.edu.java.scrapper.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfiguration {

    private final ApplicationConfig applicationConfig;

    @Bean
    public Queue defaultQueue() {
        return QueueBuilder
                .durable(applicationConfig.queueName())
                .withArgument("x-dead-letter-exchange", applicationConfig.exchangeName())
                .withArgument("x-dead-letter-routing-key", applicationConfig.routingKey() + ".dlq")
                .build();
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(applicationConfig.exchangeName(), true, false);
    }

    @Bean
    public Binding defaultBinding(Queue defaultQueue, DirectExchange directExchange){
        return BindingBuilder
                .bind(defaultQueue)
                .to(directExchange)
                .with(applicationConfig.routingKey());
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
