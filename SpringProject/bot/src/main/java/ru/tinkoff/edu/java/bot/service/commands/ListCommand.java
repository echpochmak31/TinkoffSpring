package ru.tinkoff.edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.function.Supplier;

@AllArgsConstructor
public class ListCommand implements Command {
    private final Supplier<List<String>> linksSupplier;

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "показать список отслеживаемых ссылок";
    }

    @Override
    public SendMessage handle(Update update) {
        if (linksSupplier.get().isEmpty())
            return new SendMessage(update.message().chat().id(), "Нет отслеживаемых ссылок");

        var stringBuilder = new StringBuilder();
        stringBuilder.append("Отслеживаются следующие ссылки:\n\n");

        for (var link : linksSupplier.get()) {
            stringBuilder.append(link);
            stringBuilder.append('\n');
            stringBuilder.append('\n');
        }

        return new SendMessage(update.message().chat().id(), stringBuilder.toString());
    }
}
