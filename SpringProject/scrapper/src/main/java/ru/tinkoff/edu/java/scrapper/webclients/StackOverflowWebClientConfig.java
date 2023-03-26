package ru.tinkoff.edu.java.scrapper.webclients;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StackOverflowWebClientConfig {

    @Bean(name = "stackOverflowWebClient")
    public StackOverflowWebClient stackOverflowWebClient() {
        return StackOverflowWebClient.create();
    }
}
