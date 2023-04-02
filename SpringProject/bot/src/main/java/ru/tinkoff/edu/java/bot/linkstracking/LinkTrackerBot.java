package ru.tinkoff.edu.java.bot.linkstracking;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.botcommandscope.BotCommandScopeDefault;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.linkstracking.commands.UserMessageProcessor;
import ru.tinkoff.edu.java.bot.linkstracking.replies.UserReplyProcessor;

import java.util.List;

@Service
public class LinkTrackerBot implements Bot {
    private final TelegramBot bot;
    private final UserMessageProcessor userMessageProcessor;
    private final UserReplyProcessor userReplyProcessor;

    @Autowired
    public LinkTrackerBot(@Value("${app.token}") String token,
                          @Qualifier("defaultUserMessageProcessor") UserMessageProcessor userMessageProcessor,
                          @Qualifier("defaultUserReplyProcessor") UserReplyProcessor userReplyProcessor) {
        bot = new TelegramBot(token);
        this.userMessageProcessor = userMessageProcessor;
        this.userReplyProcessor = userReplyProcessor;
        start();
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {
        bot.execute(request);
    }

    @Override
    public void start() {
        bot.setUpdatesListener(this);
        setMyCommandsMenu();
    }

    @Override
    public int process(List<Update> updates) {

        for (var update : updates) {
            var request = isReply(update) ? userReplyProcessor.process(update) : userMessageProcessor.process(update);
            execute(request);
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public void close() {
        bot.removeGetUpdatesListener();
    }

    private boolean isReply(Update update) {
        Message reply = update.message().replyToMessage();
        return reply != null;
    }

    private void setMyCommandsMenu() {
        BotCommand[] commands = userMessageProcessor.commands()
                .stream()
                .map(x -> new BotCommand(x.command(), x.description()))
                .toArray(BotCommand[]::new);

        SetMyCommands cmds = new SetMyCommands(commands);
        cmds.languageCode("ru");
        cmds.scope(new BotCommandScopeDefault());

        bot.execute(cmds);
    }

}
