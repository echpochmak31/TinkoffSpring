package ru.tinkoff.edu.java.scrapper.configuration;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfiguration {

    private final ApplicationConfig applicationConfig;

    @Value("${meta.rabbitmq.hostname}")
    private String hostname;
    @Value("${meta.rabbitmq.user}")

    private String user;
    @Value("${meta.rabbitmq.password}")
    private String password;

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(hostname);
        cachingConnectionFactory.setUsername(user);
        cachingConnectionFactory.setPassword(password);
        return cachingConnectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public Queue defaultQueue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-queue-mode", "default");
        args.put("x-dead-letter-exchange", applicationConfig.exchangeName());
        args.put("x-dead-letter-routing-key", applicationConfig.routingKey() + ".dlq");

        return QueueBuilder
            .durable(applicationConfig.queueName())
            .withArguments(args)
            .build();
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(applicationConfig.exchangeName(), true, false);
    }

    @Bean
    public Binding defaultBinding(Queue defaultQueue, DirectExchange directExchange) {
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
