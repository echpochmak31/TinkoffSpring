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
import ru.tinkoff.edu.java.scrapper.dao.models.StackOverflowLink;
import ru.tinkoff.edu.java.scrapper.dao.models.TgChat;
import ru.tinkoff.edu.java.scrapper.scheduling.apihandlers.*;
import ru.tinkoff.edu.java.scrapper.services.*;
import ru.tinkoff.edu.java.scrapper.webclients.BotHttpClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class LinkUpdaterScheduler {

    private final ChatService chatService;
    private final LinkService linkService;
    private final StackOverflowLinkService stackOverflowLinkService;
    private final StackOverflowApiService stackOverflowApiService;
    private final GitHubApiService gitHubApiService;

    private final BotHttpClient botHttpClient;
    private ApiHandler apiHandler;
    private LinkHandler linkHandler;

    private final HashMap<Long, StackOverflowLink> stackOverflowLinkCache;

    @Value("${meta.updates.check-interval}")
    private Duration duration;

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        log.info("Update!");

        var linksWithUpdates = new ArrayList<Link>();
        var links = linkService.findOldest(duration);

        loadStackOverflowCache(stackOverflowLinkService.getByIds(links.stream().map(Link::getLinkId).toList()));

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


        flushStackOverflowCache();
        linkService.refreshLastUpdate(linksWithUpdates);
    }

    @PostConstruct
    private void setHandlers() {
        linkHandler = new GitHubLinkHandler();
        var stackOverflowHandler = new StackOverflowLinkHandler();
        linkHandler.setNext(stackOverflowHandler);

        apiHandler = new GitHubApiHandler(gitHubApiService);
        var stackOverFlowApiHandler = new StackOverflowApiHandler(stackOverflowApiService, stackOverflowLinkCache);
        apiHandler.setNext(stackOverFlowApiHandler);
    }

    private void loadStackOverflowCache(List<StackOverflowLink> links) {
        links.forEach(x -> stackOverflowLinkCache.put(x.getLinkId(), x));
    }

    private void flushStackOverflowCache() {
        stackOverflowLinkService.upsertBatch(stackOverflowLinkCache.values().stream().toList());
        stackOverflowLinkCache.clear();
    }

    private void handleLink(Link link) {

    }
}

