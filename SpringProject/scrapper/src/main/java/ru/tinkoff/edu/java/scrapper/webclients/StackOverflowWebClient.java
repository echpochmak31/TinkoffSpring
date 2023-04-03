package ru.tinkoff.edu.java.scrapper.webclients;

import jakarta.validation.constraints.Min;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.webclients.dto.StackOverflowApiResponse;

@RequiredArgsConstructor
public class StackOverflowWebClient {
    private static final String baseUrl = "https://api.stackexchange.com/2.2";
    private final WebClient webClient;

    public static StackOverflowWebClient create() {
        return create(baseUrl);
    }

    public static StackOverflowWebClient create(@NonNull @URL String baseUrl) {
        WebClient newWebClient = WebClient.create(baseUrl);
        return new StackOverflowWebClient(newWebClient);
    }

    public StackOverflowApiResponse getQuestion(@NonNull @Min(0) Long questionId) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/questions/{questionId}")
                        .build(questionId)
                )
                .retrieve()
                .bodyToMono(StackOverflowApiResponse.class)
                .block();
    }
}
