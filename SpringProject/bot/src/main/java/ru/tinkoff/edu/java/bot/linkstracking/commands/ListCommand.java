package ru.tinkoff.edu.java.bot.linkstracking.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import ru.tinkoff.edu.java.bot.linkstracking.links.LinksProvider;

import java.util.List;
import java.util.function.Supplier;

@AllArgsConstructor
public class ListCommand implements Command {
    private static final String listCommand = "/list";
    private static final String listCommandDescription = "показать список отслеживаемых ссылок";
    private static final String executeMessage = "Отслеживаются следующие ссылки:\n\n";
    private static final String noLinksMessage = "Нет отслеживаемых ссылок";
    private final LinksProvider linksProvider;

    @Override
    public String command() {
        return listCommand;
    }

    @Override
    public String description() {
        return listCommandDescription;
    }

    @Override
    public SendMessage handle(Update update) {
        if (linksProvider.getLinks(update.message().chat().id()).isEmpty())
            return new SendMessage(update.message().chat().id(), noLinksMessage);

        var stringBuilder = new StringBuilder();
        stringBuilder.append(executeMessage);

        for (var link : linksProvider.getLinks(update.message().chat().id())) {
            stringBuilder.append(link);
            stringBuilder.append('\n');
            stringBuilder.append('\n');
        }

        return new SendMessage(update.message().chat().id(), stringBuilder.toString()).disableWebPagePreview(true);
    }
}
