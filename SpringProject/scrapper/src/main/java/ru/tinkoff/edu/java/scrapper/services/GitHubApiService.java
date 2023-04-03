package ru.tinkoff.edu.java.scrapper.services;


import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.webclients.GitHubWebClient;
import ru.tinkoff.edu.java.scrapper.webclients.dto.GitHubApiResponse;

@Service
public class GitHubApiService {

    private final GitHubWebClient gitHubWebClient;

    public GitHubApiService(@Qualifier("gitHubWebClient") @NonNull GitHubWebClient webClient) {
        gitHubWebClient = webClient;
    }

    public GitHubApiResponse getRepository(@NonNull String owner, @NonNull String repo) {
        return gitHubWebClient.getRepository(owner, repo);
    }
}
