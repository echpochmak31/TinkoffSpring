package ru.tinkoff.edu.java.bot.clients;

import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.bot.clients.dto.ScrapperTgChatResponse;

@Component
public class ScrapperChatsHttpClient {
    private static final String pathToTgChatById = "/tg-chat/{id}";
    @Value("${meta.scrapper.baseUrl}")
    private String baseUrl;
    private WebClient webClient;

    public ScrapperChatsHttpClient() {
    }

    public ScrapperChatsHttpClient(@NonNull @URL String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public ScrapperTgChatResponse addTgChat(@NonNull Long tgChatId) {
        return webClient
            .post()
            .uri(uriBuilder -> uriBuilder
                .path(pathToTgChatById)
                .build(tgChatId))
            .retrieve()
            .bodyToMono(ScrapperTgChatResponse.class)
            .block();
    }

    public ScrapperTgChatResponse deleteTgChat(@NonNull Long tgChatId) {
        return webClient
            .delete()
            .uri(uriBuilder -> uriBuilder
                .path(pathToTgChatById)
                .build(tgChatId))
            .retrieve()
            .bodyToMono(ScrapperTgChatResponse.class)
            .block();
    }

    @PostConstruct
    private void postConstruct() {
        if (webClient == null) {
            webClient = WebClient.create(baseUrl);
        }
    }

}
