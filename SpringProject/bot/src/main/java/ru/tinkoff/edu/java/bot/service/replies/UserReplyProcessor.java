package ru.tinkoff.edu.java.bot.service.replies;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;

public interface UserReplyProcessor {
    List<? extends Reply> replies();

    SendMessage process(Update update);
}
