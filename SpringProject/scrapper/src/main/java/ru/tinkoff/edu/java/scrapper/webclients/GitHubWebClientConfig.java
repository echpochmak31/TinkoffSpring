package ru.tinkoff.edu.java.scrapper.webclients;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GitHubWebClientConfig {
    @Value("https://api.github.com")
    private String url;

    @Bean(name = "gitHubWebClient")
    public WebClient gitHubWebClient() {
        return WebClient.create(url);
    }
}
