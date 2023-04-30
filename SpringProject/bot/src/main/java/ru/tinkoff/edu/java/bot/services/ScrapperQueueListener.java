package ru.tinkoff.edu.java.bot.services;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateMessage;
import ru.tinkoff.edu.java.bot.linkstracking.LinkTrackerBot;

@RabbitListener(queues = "${app.scrapper-queue.queue-name}")
@RequiredArgsConstructor
public class ScrapperQueueListener {
    MessageHandler messageHandler;

    @RabbitHandler
    public void receive(LinkUpdateMessage message) {
        messageHandler.handle(message);
    }
}
