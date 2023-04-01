package ru.tinkoff.edu.java.bot.service.replies;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("defaultUserReplyProcessor")
public class DefaultUserReplyProcessor implements UserReplyProcessor {
    private final List<? extends Reply> replies = List.of(
            new TrackCommandReply(),
            new UntrackCommandReply()
    );

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

        return new SendMessage(update.message().chat().id(), "Не поддерживаемый ответ");
    }
}
