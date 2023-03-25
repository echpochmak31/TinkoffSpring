package ru.tinkoff.edu.java.scrapper.services;


import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.tinkoff.edu.java.scrapper.webclients.dto.GitHubApiResponse;

@Service
public class GitHubApiService {

    public GitHubApiService(@Qualifier("gitHubWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    private final WebClient webClient;

    public GitHubApiResponse getRepository(@NonNull String owner, @NonNull String repo) {
        return webClient
                .get()
                .uri(String.join(
                        "",
                        "https://api.github.com",
                        "/repos/",
                        owner,
                        "/",
                        repo))
                .retrieve()
                .bodyToMono(GitHubApiResponse.class)
                .block();
    }
}
