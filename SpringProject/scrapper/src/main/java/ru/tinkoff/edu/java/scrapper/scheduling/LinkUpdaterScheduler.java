package ru.tinkoff.edu.java.scrapper.scheduling;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.parser.handlers.GitHubLinkHandler;
import ru.tinkoff.edu.java.parser.handlers.LinkHandler;
import ru.tinkoff.edu.java.parser.handlers.StackOverflowLinkHandler;
import ru.tinkoff.edu.java.parser.results.ParseResult;
import ru.tinkoff.edu.java.scrapper.dao.models.Link;
import ru.tinkoff.edu.java.scrapper.dao.models.TgChat;
import ru.tinkoff.edu.java.scrapper.scheduling.apihandlers.ApiHandler;
import ru.tinkoff.edu.java.scrapper.scheduling.apihandlers.ApiHandlerResult;
import ru.tinkoff.edu.java.scrapper.scheduling.apihandlers.GitHubApiHandler;
import ru.tinkoff.edu.java.scrapper.scheduling.apihandlers.StackOverflowApiHandler;
import ru.tinkoff.edu.java.scrapper.services.ChatService;
import ru.tinkoff.edu.java.scrapper.services.GitHubApiService;
import ru.tinkoff.edu.java.scrapper.services.LinkService;
import ru.tinkoff.edu.java.scrapper.services.StackOverflowApiService;
import ru.tinkoff.edu.java.scrapper.webclients.BotHttpClient;

import java.time.Duration;
import java.util.ArrayList;

@Component
@Slf4j
@RequiredArgsConstructor
public class LinkUpdaterScheduler {

    private final ChatService chatService;
    private final LinkService linkService;

    private final GitHubApiService gitHubApiService;
    private final StackOverflowApiService stackOverflowApiService;

    private final BotHttpClient botHttpClient;
    private ApiHandler apiHandler;
    private LinkHandler linkHandler;

    @Value("${meta.updates.check-interval}")
    private Duration duration;

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        log.info("Update!");

        var links = linkService.findOldest(duration);

        var linksWithUpdates = new ArrayList<Link>();

        for (var link : links) {
            var parseResult = linkHandler.handle(link.getUrl());
            var apiHandlerResult = apiHandler.handle(parseResult, link);

            if (apiHandlerResult.hasUpdate()) {
                log.info(apiHandlerResult.description());

                linksWithUpdates.add(link);
                var chatIds = chatService
                        .findAllByLinkId(link.getLinkId())
                        .stream()
                        .map(TgChat::getChatId)
                        .toArray(Long[]::new);

                botHttpClient.update(link.getLinkId(), link.getUrl(), apiHandlerResult.description(), chatIds);
            }
        }

        linkService.refreshLastUpdate(linksWithUpdates);
    }

    @PostConstruct
    private void setHandlers() {
        linkHandler = new GitHubLinkHandler();
        var stackOverflowHandler = new StackOverflowLinkHandler();
        linkHandler.setNext(stackOverflowHandler);

        apiHandler = new GitHubApiHandler(gitHubApiService);
        var stackOverFlowApiHandler = new StackOverflowApiHandler(stackOverflowApiService);
        apiHandler.setNext(stackOverFlowApiHandler);
    }
}

