package ru.tinkoff.edu.java.scrapper.webclients;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
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
        WebClient newWebClient = WebClient.create(baseUrl);
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
