package ru.tinkoff.edu.java.scrapper.webclients;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.webclients.dto.GitHubApiResponse;

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
                .uri(String.join(
                        "",
                        "/repos/",
                        owner,
                        "/",
                        repo))
                .retrieve()
                .bodyToMono(GitHubApiResponse.class)
                .block();
    }
}
