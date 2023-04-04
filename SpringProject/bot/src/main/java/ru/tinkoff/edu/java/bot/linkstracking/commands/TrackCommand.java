package ru.tinkoff.edu.java.bot.linkstracking.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ForceReply;
import com.pengrad.telegrambot.request.SendMessage;

import static ru.tinkoff.edu.java.bot.linkstracking.commands.TrackCommandConstants.*;

public class TrackCommand implements Command {

    @Override
    public String command() {
        return trackCommand;
    }

    @Override
    public String description() {
        return trackCommandDescription;
    }

    @Override
    public SendMessage handle(Update update) {
        return new SendMessage(update.message().chat().id(), trackCommandExecuteMessage).replyMarkup(new ForceReply());
    }
}
