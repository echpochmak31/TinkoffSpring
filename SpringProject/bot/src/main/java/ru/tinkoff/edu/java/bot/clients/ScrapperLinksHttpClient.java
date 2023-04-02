package ru.tinkoff.edu.java.bot.clients;

import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.clients.dto.ScrapperLinkRequest;
import ru.tinkoff.edu.java.bot.clients.dto.ScrapperLinkResponse;
import ru.tinkoff.edu.java.bot.clients.dto.ScrapperListLinkResponse;

@Component
public class ScrapperLinksHttpClient {
    @Value("${meta.scrapper.baseUrl}")
    private String baseUrl;
    private WebClient webClient;

    public ScrapperLinksHttpClient() { }

    public ScrapperLinksHttpClient(@NonNull @URL String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public ScrapperListLinkResponse getLinks(@NonNull Long tgChatId) {
        return webClient
                .get()
                .uri("/links")
                .header("Tg-Chat-Id", tgChatId.toString())
                .retrieve()
                .bodyToMono(ScrapperListLinkResponse.class)
                .block();
    }

    public ScrapperLinkResponse addLink(@NonNull Long tgChatId, @NonNull @URL String link) {
        return webClient
                .post()
                .uri("/links")
                .header("Tg-Chat-Id", tgChatId.toString())
                .body(Mono.just(new ScrapperLinkRequest(link)), ScrapperLinkRequest.class)
                .retrieve()
                .bodyToMono(ScrapperLinkResponse.class)
                .block();
    }

    public ScrapperLinkResponse deleteLink(@NonNull Long tgChatId, @NonNull @URL String link) {
        return webClient
                .method(HttpMethod.DELETE)
                .uri("/links")
                .header("Tg-Chat-Id", tgChatId.toString())
                .body(Mono.just(new ScrapperLinkRequest(link)), ScrapperLinkRequest.class)
                .retrieve()
                .bodyToMono(ScrapperLinkResponse.class)
                .block();
    }

    @PostConstruct
    private void postConstruct() {
        if (webClient == null) {
            webClient = WebClient.create(baseUrl);
        }
    }

}
