package ru.tinkoff.edu.java.bot.services;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateMessage;
import ru.tinkoff.edu.java.bot.linkstracking.LinkTrackerBot;

@Service
@RequiredArgsConstructor
public class LinkUpdateMessageHandler implements MessageHandler {
    private final LinkTrackerBot bot;

    public void handle(@NonNull String url, @NonNull String description, @NonNull Long[] tgChatIds) {
        String notificationMessage = getNotificationMessage(url, description);

        for (long chatId : tgChatIds) {
            SendMessage sendMessage = new SendMessage(chatId, notificationMessage)
                    .parseMode(ParseMode.Markdown)
                    .disableWebPagePreview(true);

            bot.execute(sendMessage);
        }
    }

    private String getNotificationMessage(String url, String description) {
        var stringBuilder = new StringBuilder();

        stringBuilder.append("Есть обновления для ссылки:\n");
        stringBuilder.append(url);
        stringBuilder.append('\n');
        stringBuilder.append(description);

        return stringBuilder.toString();
    }
}
