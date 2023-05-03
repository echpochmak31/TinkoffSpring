package ru.tinkoff.edu.java.bot.linkstracking.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.AllArgsConstructor;
import java.util.function.Consumer;

@AllArgsConstructor
public class StartCommand implements Command {
    private static final String startCommand = "/start";
    private static final String startCommandDescription = "зарегистрировать пользователя";
    private static final String executeMessage = "Поехали!";
    private final Consumer<User> userConsumer;

    @Override
    public String command() {
        return startCommand;
    }

    @Override
    public String description() {
        return startCommandDescription;
    }

    @Override
    public SendMessage handle(Update update) {
        User user = update.message().from();
        userConsumer.accept(user);
        return new SendMessage(update.message().chat().id(), executeMessage);
    }
}
