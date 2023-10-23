package ru.tinkoff.edu.java.bot.tests;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.util.ReflectionTestUtils;
import ru.tinkoff.edu.java.bot.linkstracking.LinkTrackerBot;
import ru.tinkoff.edu.java.bot.linkstracking.commands.DefaultUserMessageProcessor;
import ru.tinkoff.edu.java.bot.linkstracking.links.LinksRepository;
import ru.tinkoff.edu.java.bot.linkstracking.replies.DefaultUserReplyProcessor;
import ru.tinkoff.edu.java.bot.linkstracking.users.UserRepository;
import ru.tinkoff.edu.java.bot.services.ScrapperApiService;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("checkstyle:RegexpSingleline")
@ExtendWith(MockitoExtension.class)
public class BotTests {
    List<Update> updates;
    Update fakeUpdate;

    @Value("${meta.paths.help-command-md}")
    String path;
    LinksRepository linksRepository;
    LinkTrackerBot linkTrackerBot;
    @Mock
    TelegramBot bot;
    @Mock
    ScrapperApiService scrapperApiService;
    @Captor
    ArgumentCaptor<? extends BaseRequest<SendMessage, SendResponse>> sendMessageCaptor;

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    private Update setupUpdate(String fakeText) {
        Message fakeMessage = new Message();
        Chat fakeChat = new Chat();
        Update result = new Update();

        ReflectionTestUtils.setField(fakeChat, "id", 1L);
        ReflectionTestUtils.setField(fakeMessage, "text", fakeText);
        ReflectionTestUtils.setField(fakeMessage, "chat", fakeChat);
        ReflectionTestUtils.setField(result, "message", fakeMessage);

        return result;
    }

    @BeforeEach
    public void setup() {
        updates = new ArrayList<>();

//        Mockito.when(scrapperApiService.addLink());

        linksRepository = new LinksRepository(scrapperApiService);
        var userInfoRepository = new UserRepository(new HashMap<>());
        var messageProcessor = new DefaultUserMessageProcessor(linksRepository, userInfoRepository, path);
        var replyProcessor = new DefaultUserReplyProcessor(linksRepository);
        linkTrackerBot = new LinkTrackerBot("fakeToken", messageProcessor, replyProcessor, scrapperApiService);

        ReflectionTestUtils.setField(linkTrackerBot, "bot", bot);
    }

    /*
    @Test
    public void listCommandEmptyListTest() {
        // given
        fakeUpdate = setupUpdate("/list");
        updates.add(fakeUpdate);

        // when
        linkTrackerBot.process(updates);

        // then
        Mockito.verify(bot).execute(sendMessageCaptor.capture());

        BaseRequest<SendMessage, SendResponse> value = sendMessageCaptor.getValue();
        String realText = (String) value.getParameters().get("text");

        assertAll(
            () -> assertEquals("/list", fakeUpdate.message().text()),
            () -> assertEquals("Нет отслеживаемых ссылок", realText)
        );

    }

    @Test
    public void listCommandTest() {
        // given
        String link = "https://stackoverflow.com";
        ReflectionTestUtils.setField(linksRepository, "links", List.of(link));
        fakeUpdate = setupUpdate("/list");
        updates.add(fakeUpdate);

        // when
        linkTrackerBot.process(updates);

        // then
        Mockito.verify(bot).execute(sendMessageCaptor.capture());

        BaseRequest<SendMessage, SendResponse> value = sendMessageCaptor.getValue();
        String realText = (String) value.getParameters().get("text");

        assertAll(
            () -> assertEquals("/list", fakeUpdate.message().text()),
            () -> assertEquals("Отслеживаются следующие ссылки:\n\n" + link + "\n\n", realText)
        );
    }

    */

    @Test
    public void unknownCommandTest() {
        // given
        String unknownCommand = "/unknownCommand";
        fakeUpdate = setupUpdate(unknownCommand);
        updates.add(fakeUpdate);

        // when
        linkTrackerBot.process(updates);

        // then
        Mockito.verify(bot).execute(sendMessageCaptor.capture());

        BaseRequest<SendMessage, SendResponse> value = sendMessageCaptor.getValue();
        String realText = (String) value.getParameters().get("text");

        assertAll(
            () -> assertEquals(unknownCommand, fakeUpdate.message().text()),
            () -> assertEquals("Неподдерживаемая команда", realText)
        );
    }
}
