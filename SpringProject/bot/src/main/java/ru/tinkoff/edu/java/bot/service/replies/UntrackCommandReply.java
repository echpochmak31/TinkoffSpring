package ru.tinkoff.edu.java.bot.service.replies;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.apache.commons.validator.routines.UrlValidator;

public class UntrackCommandReply implements Reply {
    private static final UrlValidator urlValidator = new UrlValidator();
    @Override
    public String reply() {
        return "untrack";
    }

    @Override
    public SendMessage handle(Update update) {
        if (urlValidator.isValid(update.message().text()))
            return new SendMessage(update.message().chat().id(), "Отслеживание ссылки прекращено");
        return new SendMessage(update.message().chat().id(), "Не корректная ссылка");
    }
}
