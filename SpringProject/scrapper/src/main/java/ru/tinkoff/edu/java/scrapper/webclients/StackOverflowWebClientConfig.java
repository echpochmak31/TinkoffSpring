package ru.tinkoff.edu.java.scrapper.webclients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class StackOverflowWebClientConfig {

    @Value("https://api.stackexchange.com/2.2")
    private String url;

    @Bean(name = "stackOverflowWebClient")
    public WebClient stackOverflowWebClient() {
        return WebClient.create(url);
    }
}
