package ru.tinkoff.edu.java.bot.services;

import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateMessage;
import ru.tinkoff.edu.java.bot.linkstracking.LinkTrackerBot;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class LinkUpdateMessageHandler {
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

    private static String getNotificationMessage(String url, String description) {

        return "Есть обновления для ссылки:\n" +
                url +
                '\n' +
                description;
    }
}
