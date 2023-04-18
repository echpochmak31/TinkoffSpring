package ru.tinkoff.edu.java.scrapper.webclients;

import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.scrapper.webclients.dto.BotLinkUpdateRequest;
import ru.tinkoff.edu.java.scrapper.webclients.dto.BotLinkUpdateResponse;

import java.net.URI;

@Component
public class BotHttpClient {
    private static final String pathToUpdates = "/updates";
    @Value("${meta.bot.baseUrl}")
    private String baseUrl;
    private WebClient webClient;

    public BotHttpClient() { }

    public BotHttpClient(@NonNull @URL String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public BotLinkUpdateResponse update(long linkId, String url, String description, Long[] tgChatIds) {
        return webClient
                .post()
                .uri(UriBuilder -> UriBuilder
                        .path(pathToUpdates)
                        .build())
                .body(Mono.just(new BotLinkUpdateRequest(linkId, URI.create(url), description, tgChatIds)), BotLinkUpdateRequest.class)
                .retrieve()
                .bodyToMono(BotLinkUpdateResponse.class)
                .block();
    }

    @PostConstruct
    private void postConstruct() {
        if (webClient == null) {
            webClient = WebClient.create(baseUrl);
        }
    }
}
