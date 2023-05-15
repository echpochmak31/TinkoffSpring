package ru.tinkoff.edu.java.bot.linkstracking.links;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.bot.services.ScrapperApiService;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LinksRepository implements LinksProvider, LinksTracker, LinksUntracker {
    private final ScrapperApiService scrapperApiService;

    @Override
    public void track(long tgChatId, String link) {
        scrapperApiService.addLink(tgChatId, link);
    }

    @Override
    public void untrack(long tgChatId, String link) {
        scrapperApiService.deleteLink(tgChatId, link);
    }

    @Override
    public List<String> getLinks(long tgChatId) {
        return Arrays.stream(scrapperApiService.getLinks(tgChatId).links()).map(x -> x.url().toString()).toList();
    }
}
