package ru.tinkoff.edu.java.bot.clients;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.clients.dto.ScrapperLinkRequest;
import ru.tinkoff.edu.java.bot.clients.dto.ScrapperLinkResponse;
import ru.tinkoff.edu.java.bot.clients.dto.ScrapperListLinkResponse;
import ru.tinkoff.edu.java.bot.clients.dto.ScrapperTgChatResponse;

@Component
@RequiredArgsConstructor
public class ScrapperChatsHttpClient {
    private static String baseUrl = "http://localhost:8082";

    private final WebClient webClient;

    public ScrapperChatsHttpClient() {
        webClient = WebClient.create(baseUrl);
    }

    public ScrapperChatsHttpClient(@NonNull @URL String baseUrl) {
        ScrapperChatsHttpClient.baseUrl = baseUrl;
        webClient = WebClient.create(baseUrl);
    }

    public ScrapperTgChatResponse addTgChat(@NonNull Long tgChatId) {
        return webClient
                .post()
                .uri(UriBuilder -> UriBuilder
                        .path("/tg-chat/{id}")
                        .build(tgChatId))
                .retrieve()
                .bodyToMono(ScrapperTgChatResponse.class)
                .block();
    }

    public ScrapperTgChatResponse deleteTgChat(@NonNull Long tgChatId) {
        return webClient
                .delete()
                .uri(UriBuilder -> UriBuilder
                        .path("/tg-chat/{id}")
                        .build(tgChatId))
                .retrieve()
                .bodyToMono(ScrapperTgChatResponse.class)
                .block();
    }
}
