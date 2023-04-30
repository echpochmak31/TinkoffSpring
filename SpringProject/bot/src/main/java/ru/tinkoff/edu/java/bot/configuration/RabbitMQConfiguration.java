package ru.tinkoff.edu.java.bot.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfiguration {
    private final ApplicationConfig applicationConfig;

    @Bean
    public Queue defaultQueue() {
        return new Queue(applicationConfig.scrapperQueue().queueName(), false);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(applicationConfig.scrapperQueue().exchangeName(), true, false);
    }

    @Bean
    public Binding defaultBinding(Queue defaultQueue, DirectExchange directExchange){
        return BindingBuilder
                .bind(defaultQueue)
                .to(directExchange)
                .with(applicationConfig.scrapperQueue().routingKey());
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
