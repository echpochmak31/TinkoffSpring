package ru.tinkoff.edu.java.scrapper.scheduling;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.parser.handlers.LinkHandler;
import ru.tinkoff.edu.java.scrapper.dao.models.Link;
import ru.tinkoff.edu.java.scrapper.dao.models.StackOverflowLink;
import ru.tinkoff.edu.java.scrapper.dao.models.TgChat;
import ru.tinkoff.edu.java.scrapper.dto.LinkUpdateMessage;
import ru.tinkoff.edu.java.scrapper.scheduling.apihandlers.ApiHandler;
import ru.tinkoff.edu.java.scrapper.scheduling.apihandlers.GitHubApiHandler;
import ru.tinkoff.edu.java.scrapper.scheduling.apihandlers.StackOverflowApiHandler;
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

    private final MessageSender messageSender;
    private final ApiHandler apiHandler;
    private final LinkHandler linkHandler;

    private final HashMap<Long, StackOverflowLink> stackOverflowLinkCache;

    @Value("${meta.updates.check-interval}")
    private Duration duration;

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        log.info("Проверка обновлений");

        var linksWithUpdates = new ArrayList<Link>();
        var links = linkService.findOldest(duration);
        linkService.updateOldest(links);

        loadStackOverflowCache(stackOverflowLinkService.getByIds(links.stream().map(Link::getLinkId).toList()));

        for (var link : links) {
            var parseResult = linkHandler.handle(link.getUrl());
            var apiHandlerResult = apiHandler.handle(parseResult, link);

            if (apiHandlerResult.hasUpdate()) {
                log.info("Есть обновления для ссылки " + link.getUrl());

                linksWithUpdates.add(link);
                var chatIds = chatService
                        .findAllByLinkId(link.getLinkId())
                        .stream()
                        .map(TgChat::getChatId)
                        .toArray(Long[]::new);


                var updateMessage = LinkUpdateMessage.builder()
                        .linkId(link.getLinkId())
                        .url(link.getUrl())
                        .description(apiHandlerResult.description())
                        .tgChatIds(chatIds)
                        .build();

                messageSender.sendToBot(updateMessage);
            }
        }


        flushStackOverflowCache();
        linkService.refreshLastUpdate(linksWithUpdates);
    }

    private void loadStackOverflowCache(List<StackOverflowLink> links) {
        links.forEach(x -> stackOverflowLinkCache.put(x.getLinkId(), x));
    }

    private void flushStackOverflowCache() {
        stackOverflowLinkService.upsertBatch(stackOverflowLinkCache.values().stream().toList());
        stackOverflowLinkCache.clear();
    }

}

