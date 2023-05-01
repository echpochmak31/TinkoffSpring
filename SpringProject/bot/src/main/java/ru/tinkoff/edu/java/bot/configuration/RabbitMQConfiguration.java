package ru.tinkoff.edu.java.bot.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateMessage;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfiguration {
    private final ApplicationConfig applicationConfig;

    @Bean
    public Queue defaultQueue() {
        return QueueBuilder
                .durable(applicationConfig.scrapperQueue().queueName())
                .withArgument("x-dead-letter-exchange", applicationConfig.scrapperQueue().exchangeName())
                .withArgument("x-dead-letter-routing-key", applicationConfig.scrapperQueue().routingKey() + ".dlq")
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
