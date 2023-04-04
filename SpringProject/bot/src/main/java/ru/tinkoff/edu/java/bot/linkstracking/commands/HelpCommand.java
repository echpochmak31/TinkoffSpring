package ru.tinkoff.edu.java.bot.linkstracking.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.NonNull;

public class HelpCommand implements Command {
    private static final String helpCommand = "/help";
    private static final String helpCommandDescription = "вывести окно с командами";
    private final String markdownMessage;
    public HelpCommand(@NonNull String markdownMessage) {
        this.markdownMessage = markdownMessage;
    }
    @Override
    public String command() {
        return helpCommand;
    }

    @Override
    public String description() {
        return helpCommandDescription;
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), markdownMessage).parseMode(ParseMode.Markdown);
    }
}
