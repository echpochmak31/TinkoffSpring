package ru.tinkoff.edu.java.scrapper.services;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdateMessage;

@Service
@RequiredArgsConstructor
public class ScrapperQueueProducer {

    private final RabbitTemplate rabbitTemplate;

    public void send(String exchange, String routingKey, LinkUpdateMessage message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
