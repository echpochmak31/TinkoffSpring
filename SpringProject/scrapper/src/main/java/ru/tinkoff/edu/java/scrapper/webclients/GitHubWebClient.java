package ru.tinkoff.edu.java.scrapper.webclients;

import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import ru.tinkoff.edu.java.scrapper.webclients.dto.GitHubApiResponse;
import ru.tinkoff.edu.java.scrapper.webclients.dto.StackOverflowApiResponse;

@RequiredArgsConstructor
public class GitHubWebClient {
    private static final String baseUrl = "https://api.github.com";
    private final WebClient webClient;

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
}
