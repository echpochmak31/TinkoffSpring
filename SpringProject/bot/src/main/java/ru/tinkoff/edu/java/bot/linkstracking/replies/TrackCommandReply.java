package ru.tinkoff.edu.java.bot.linkstracking.replies;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import ru.tinkoff.edu.java.bot.linkstracking.links.LinksTracker;

@AllArgsConstructor
public class TrackCommandReply implements Reply {
    private static final UrlValidator urlValidator = new UrlValidator();
    private final LinksTracker linksTracker;
    @Override
    public String reply() {
        return "Какую ссылку будем отслеживать?";
    }

    @Override
    public SendMessage handle(Update update) {
        if (!urlValidator.isValid(update.message().text()))
            return new SendMessage(update.message().chat().id(), "Не корректная ссылка");

        linksTracker.track(update.message().text());
        return new SendMessage(update.message().chat().id(), "Отслеживание ссылки начато");
    }
}
