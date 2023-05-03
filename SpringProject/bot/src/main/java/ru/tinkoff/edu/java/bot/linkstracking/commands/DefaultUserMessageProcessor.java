package ru.tinkoff.edu.java.bot.linkstracking.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.linkstracking.links.LinksRepository;
import ru.tinkoff.edu.java.bot.linkstracking.users.UserRepository;

@Component("defaultUserMessageProcessor")
public class DefaultUserMessageProcessor implements UserMessageProcessor {
    private static final String helpCommandUnavailableMessage = "Недоступно";
    private static final String unsupportedCommandMessage = "Неподдерживаемая команда";
    private final List<? extends Command> commands;

    public DefaultUserMessageProcessor(
        @Autowired LinksRepository linksRepository,
        @Autowired UserRepository userRepository,
        @Value("${meta.paths.help-command-md}") String pathToMarkdownFile
    ) {

        String markdownMessage;
        try {
            markdownMessage = new String(Files.readAllBytes(Paths.get(pathToMarkdownFile)));
        } catch (Exception e) {
            markdownMessage = helpCommandUnavailableMessage;
        }

        commands = List.of(
            new StartCommand(userRepository),
            new HelpCommand(markdownMessage),
            new TrackCommand(),
            new UntrackCommand(),
            new ListCommand(linksRepository)
        );
    }

    @Override
    public List<? extends Command> commands() {
        return commands;
    }

    @Override
    public SendMessage process(Update update) {
        for (var command : commands) {
            if (command.supports(update)) {
                return command.handle(update);
            }
        }
        return new SendMessage(update.message().chat().id(), unsupportedCommandMessage);
    }
}
