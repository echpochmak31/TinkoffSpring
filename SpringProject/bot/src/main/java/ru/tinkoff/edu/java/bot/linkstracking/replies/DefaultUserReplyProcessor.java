package ru.tinkoff.edu.java.bot.linkstracking.replies;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.linkstracking.links.LinksRepository;

import java.util.List;

@Component("defaultUserReplyProcessor")
public class DefaultUserReplyProcessor implements UserReplyProcessor {
    private static final String unsupportedReplyMessage = "Не поддерживаемый ответ";
    private final List<? extends Reply> replies;

    public DefaultUserReplyProcessor(@Autowired LinksRepository linksRepository) {
        replies = List.of(
                new TrackCommandReply(linksRepository),
                new UntrackCommandReply(linksRepository)
        );
    }


    @Override
    public List<? extends Reply> replies() {
        return replies;
    }

    @Override
    public SendMessage process(Update update) {

        for (var reply : replies) {
            if (reply.supports(update))
                return reply.handle(update);
        }

        return new SendMessage(update.message().chat().id(), unsupportedReplyMessage);
    }
}
