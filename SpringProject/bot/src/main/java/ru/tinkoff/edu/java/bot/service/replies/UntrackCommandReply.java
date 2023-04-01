package ru.tinkoff.edu.java.bot.service.replies;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import ru.tinkoff.edu.java.bot.service.links.LinksUntracker;

@AllArgsConstructor
public class UntrackCommandReply implements Reply {
    private static final UrlValidator urlValidator = new UrlValidator();
    private final LinksUntracker linksUntracker;
    @Override
    public String reply() {
        return "Какую ссылку перестанем отслеживать?";
    }

    @Override
    public SendMessage handle(Update update) {
        if (!urlValidator.isValid(update.message().text()))
            return new SendMessage(update.message().chat().id(), "Не корректная ссылка");

        linksUntracker.untrack(update.message().text());
        return new SendMessage(update.message().chat().id(), "Отслеживание ссылки прекращено");
    }
}
