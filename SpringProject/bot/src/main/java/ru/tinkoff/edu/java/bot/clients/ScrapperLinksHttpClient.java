package ru.tinkoff.edu.java.bot.clients;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.java.bot.clients.dto.ScrapperLinkRequest;
import ru.tinkoff.edu.java.bot.clients.dto.ScrapperLinkResponse;
import ru.tinkoff.edu.java.bot.clients.dto.ScrapperListLinkResponse;

@Component
@RequiredArgsConstructor
//@PropertySource("classpath:application.yml")
public class ScrapperLinksHttpClient {
//    @Value("${util.scrapper.url}")
    private static String baseUrl = "http://localhost:8082";

    private final WebClient webClient;

    public ScrapperLinksHttpClient() {
        webClient = WebClient.create(baseUrl);
    }

    public ScrapperLinksHttpClient(@NonNull @URL String baseUrl) {
        ScrapperLinksHttpClient.baseUrl = baseUrl;
        webClient = WebClient.create(baseUrl);
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

}
