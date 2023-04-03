package ru.tinkoff.edu.java.scrapper.webclients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitHubWebClientConfig {

    @Bean(name = "gitHubWebClient")
    public GitHubWebClient gitHubWebClient() {
        return GitHubWebClient.create();
    }

}
