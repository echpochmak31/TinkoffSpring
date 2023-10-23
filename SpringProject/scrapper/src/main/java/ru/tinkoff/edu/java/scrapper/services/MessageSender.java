package ru.tinkoff.edu.java.scrapper.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.configuration.ApplicationConfig;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdateMessage;
import ru.tinkoff.edu.java.scrapper.webclients.BotHttpClient;

@Service
@RequiredArgsConstructor
public class MessageSender {
    private final ScrapperQueueProducer scrapperQueueProducer;
    private final BotHttpClient botHttpClient;
    private final ApplicationConfig applicationConfig;

    public void sendToBot(LinkUpdateMessage message) {
        if (applicationConfig.useQueue()) {
            scrapperQueueProducer.send(applicationConfig.exchangeName(), applicationConfig.routingKey(), message);
        } else {
            botHttpClient.update(message.linkId(), message.url(), message.description(), message.tgChatIds());
        }
    }
}
