package ru.tinkoff.edu.java.bot.linkstracking.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

public interface Command {
    String command();

    String description();

    SendMessage handle(Update update);

    default boolean supports(Update update) {
        return update != null &&
                update.message() != null &&
                update.message().text() != null &&
                update.message().text().startsWith(command());
    }

    default BotCommand toApiCommand() {
        return new BotCommand(command(), description());
    }
}