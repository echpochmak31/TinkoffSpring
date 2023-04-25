package ru.tinkoff.edu.java.scrapper.webclients;

import io.netty.resolver.DefaultAddressResolverGroup;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import ru.tinkoff.edu.java.scrapper.webclients.dto.github.GitHubApiEventResponse;
import ru.tinkoff.edu.java.scrapper.webclients.dto.github.GitHubApiResponse;

@RequiredArgsConstructor
public class GitHubWebClient {
    private static final String baseUrl = "https://api.github.com";
    private final WebClient webClient;
    @Value("${meta.updates.github-events-per-page}")
    private String eventsPerPageRequestParam = "1";

    public static GitHubWebClient create() {
        return create(baseUrl);
    }

    public static GitHubWebClient create(String baseUrl) {
        HttpClient httpClient = HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);

        WebClient newWebClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(baseUrl)
                .build();

        return new GitHubWebClient(newWebClient);
    }

    public GitHubApiResponse getRepository(@NonNull String owner, @NonNull String repo) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/repos/{owner}/{repo}")
                        .build(owner, repo)
                )
                .retrieve()
                .bodyToMono(GitHubApiResponse.class)
                .block();
    }

    public GitHubApiEventResponse[] getEvents(@NonNull String owner, @NonNull String repo) {
        var multiValueMap = new LinkedMultiValueMap<String, String>();
        multiValueMap.add("per_page", eventsPerPageRequestParam);

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/repos/{owner}/{repo}/events")
                        .queryParams(multiValueMap)
                        .build(owner, repo)
                )
                .retrieve()
                .bodyToMono(GitHubApiEventResponse[].class)
                .block();
    }
}
