package ru.tinkoff.edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;

import java.util.function.Consumer;

@AllArgsConstructor
public class StartCommand implements Command {
    private final Consumer<User> userConsumer;
    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "зарегистрировать пользователя";
    }

    @Override
    public SendMessage handle(Update update) {
        User user = update.message().from();
        userConsumer.accept(user);
        return new SendMessage(update.message().chat().id(), "Поехали!");
    }
}
