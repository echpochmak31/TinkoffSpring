package ru.tinkoff.edu.java.scrapper.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfiguration {

    private final ApplicationConfig applicationConfig;

    @Bean
    public Queue defaultQueue() {
        return new Queue(applicationConfig.queueName(), false);
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
