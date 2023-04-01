package ru.tinkoff.edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.service.commands.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Component("defaultUserMessageProcessor")
public class DefaultUserMessageProcessor implements UserMessageProcessor {

    public DefaultUserMessageProcessor(@Value("${util.commands.help-md-path}") String pathToMarkdownFile) {
        String markdownMessage;
        try {
            markdownMessage = new String(Files.readAllBytes(Paths.get(pathToMarkdownFile)));
        }
        catch (Exception e) {
            markdownMessage = "Недоступно";
        }

        commands = List.of(
                new StartCommand(),
                new HelpCommand(markdownMessage),
                new TrackCommand(),
                new UntrackCommand(),
                new ListCommand()
        );
    }
    private final List<? extends Command> commands;
    @Override
    public List<? extends Command> commands() {
        return commands;
    }

    @Override
    public SendMessage process(Update update) {
        for (var command : commands) {
            if (command.supports(update))
                return command.handle(update);
        }
        return new SendMessage(update.message().chat().id(), "Неподдерживаемая команда");
    }
}