package ru.tinkoff.edu.java.bot.services;

import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateMessage;
import ru.tinkoff.edu.java.bot.linkstracking.LinkTrackerBot;

@Service
@RequiredArgsConstructor
public class LinkUpdateMessageHandler implements MessageHandler {
    private final LinkTrackerBot bot;

    public void handle(LinkUpdateMessage linkUpdateMessage) {
        String notificationMessage = getNotificationMessage(linkUpdateMessage);

        for (long chatId : linkUpdateMessage.tgChatIds()) {
            SendMessage sendMessage = new SendMessage(chatId, notificationMessage);
            bot.execute(sendMessage);
        }
    }

    private String getNotificationMessage(LinkUpdateMessage linkUpdateMessage) {
        var stringBuilder = new StringBuilder();

        stringBuilder.append("Есть обновления для ссылки:\n");
        stringBuilder.append(linkUpdateMessage.url());
        stringBuilder.append('\n');
        stringBuilder.append(linkUpdateMessage.description());

        return stringBuilder.toString();
    }
}
