package ru.tinkoff.edu.java.bot.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateMessage;

import java.util.HashMap;
import java.util.Map;

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
        args.put("x-dead-letter-exchange", applicationConfig.scrapperQueue().exchangeName());
        args.put("x-dead-letter-routing-key",  applicationConfig.scrapperQueue().routingKey() + ".dlq");

        return QueueBuilder
                .durable(applicationConfig.scrapperQueue().queueName())
                .withArguments(args)
                .build();
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(applicationConfig.scrapperQueue().exchangeName(), true, false);
    }

    @Bean
    public Binding defaultBinding(@Qualifier("defaultQueue") Queue defaultQueue, DirectExchange directExchange) {
        return BindingBuilder
                .bind(defaultQueue)
                .to(directExchange)
                .with(applicationConfig.scrapperQueue().routingKey());
    }

    @Bean
    public ClassMapper classMapper() {
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("ru.tinkoff.edu.java.scrapper.dto.LinkUpdateMessage", LinkUpdateMessage.class);

        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("ru.tinkoff.edu.java.scrapper.dto.*");
        classMapper.setIdClassMapping(mappings);
        return classMapper;
    }

    @Bean
    public MessageConverter jsonMessageConverter(ClassMapper classMapper) {
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper);
        return jsonConverter;
    }

}
