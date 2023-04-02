package ru.tinkoff.edu.java.bot.linkstracking.replies;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface Reply {
    String reply();
    SendMessage handle(Update update);
    default boolean supports(Update update) {
        Message reply = update.message().replyToMessage();
        return reply != null && reply.text().equals(reply());
    }
}
