package ru.tinkoff.edu.java.bot.services;

import jakarta.validation.constraints.Min;
import lombok.NonNull;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.bot.clients.ScrapperChatsHttpClient;
import ru.tinkoff.edu.java.bot.clients.ScrapperLinksHttpClient;
import ru.tinkoff.edu.java.bot.clients.dto.ScrapperLinkResponse;
import ru.tinkoff.edu.java.bot.clients.dto.ScrapperListLinkResponse;
import ru.tinkoff.edu.java.bot.clients.dto.ScrapperTgChatResponse;

@Service
public class ScrapperApiService {

    private final ScrapperLinksHttpClient linksClient;
    private final ScrapperChatsHttpClient chatsClient;

    public ScrapperApiService(
            @Autowired ScrapperLinksHttpClient linksClient,
            @Autowired ScrapperChatsHttpClient chatsClient) {
        this.linksClient = linksClient;
        this.chatsClient = chatsClient;
    }

    public ScrapperListLinkResponse getLinks(@NonNull @Min(0) Long tgChatId) {
        return linksClient.getLinks(tgChatId);
    }

    public ScrapperLinkResponse addLink(@NonNull @Min(0) Long tgChatId, @NonNull @URL String link) {
        return linksClient.addLink(tgChatId, link);
    }

    public ScrapperLinkResponse deleteLink(@NonNull @Min(0) Long tgChatId, @NonNull @URL String link) {
        return linksClient.deleteLink(tgChatId, link);
    }

    public ScrapperTgChatResponse addTgChat(@NonNull @Min(0) Long tgChatId) {
        return chatsClient.addTgChat(tgChatId);
    }

    public ScrapperTgChatResponse deleteTgChat(@NonNull @Min(0) Long tgChatId) {
        return chatsClient.deleteTgChat(tgChatId);
    }

}
