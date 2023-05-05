package ru.tinkoff.edu.java.bot.linkstracking.replies;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import ru.tinkoff.edu.java.bot.linkstracking.commands.UntrackCommandConstants;
import ru.tinkoff.edu.java.bot.linkstracking.links.LinksUntracker;

@AllArgsConstructor
public class UntrackCommandReply implements Reply {
    private static final String invalidLinkMessage = "Не корректная ссылка";
    private static final String stopTrackingMessage = "Отслеживание ссылки прекращено";
    private static final UrlValidator urlValidator = new UrlValidator();
    private final LinksUntracker linksUntracker;
    @Override
    public String reply() {
        return UntrackCommandConstants.untrackCommandExecuteMessage;
    }

    @Override
    public SendMessage handle(Update update) {
        if (!urlValidator.isValid(update.message().text()))
            return new SendMessage(update.message().chat().id(), invalidLinkMessage);

        linksUntracker.untrack(update.message().chat().id(), update.message().text());
        return new SendMessage(update.message().chat().id(), stopTrackingMessage);
    }
}
