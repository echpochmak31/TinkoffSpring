package ru.tinkoff.edu.java.scrapper.webclients;

import jakarta.validation.constraints.Min;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.webclients.dto.StackOverflowApiResponse;

@RequiredArgsConstructor
public class StackOverflowWebClient {
    private static final String baseUrl = "https://api.stackexchange.com/2.3";
    private final WebClient webClient;

    public static StackOverflowWebClient create() {
        return create(baseUrl);
    }

    public static StackOverflowWebClient create(@NonNull @URL String baseUrl) {
        WebClient newWebClient = WebClient.create(baseUrl);
        return new StackOverflowWebClient(newWebClient);
    }

    public StackOverflowApiResponse getQuestion(@NonNull @Min(0) Long questionId) {
        var multiValueMap = new LinkedMultiValueMap<String, String>();
        multiValueMap.add("order", "desc");
        multiValueMap.add("sort", "activity");
        multiValueMap.add("site", "stackoverflow");
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/questions/{questionId}")
                        .queryParams(multiValueMap)
                        .build(questionId)
                )
                .retrieve()
                .bodyToMono(StackOverflowApiResponse.class)
                .block();
    }
}
