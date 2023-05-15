package ru.tinkoff.edu.java.bot.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateMessage;

@RabbitListener(queues = "${app.scrapper-queue.queue-name}")
@RequiredArgsConstructor
@Component
public class ScrapperQueueListener {
    private final LinkUpdateMessageHandler messageHandler;

    @RabbitHandler
    public void receive(LinkUpdateMessage message) {
        messageHandler.handle(message.url(), message.description(), message.tgChatIds());
    }
}
