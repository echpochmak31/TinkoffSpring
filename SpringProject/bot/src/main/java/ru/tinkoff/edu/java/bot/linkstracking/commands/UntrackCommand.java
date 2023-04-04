package ru.tinkoff.edu.java.bot.linkstracking.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;

import static ru.tinkoff.edu.java.bot.linkstracking.commands.UntrackCommandConstants.*;

public class UntrackCommand implements Command {
    @Override
    public String command() {
        return untrackCommand;
    }

    @Override
    public String description() {
        return untrackCommandDescription;
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), untrackCommandExecuteMessage).replyMarkup(new ForceReply());
    }
}
