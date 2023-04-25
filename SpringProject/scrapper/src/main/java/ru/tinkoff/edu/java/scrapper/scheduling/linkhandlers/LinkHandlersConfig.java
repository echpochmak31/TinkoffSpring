package ru.tinkoff.edu.java.scrapper.scheduling.linkhandlers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.tinkoff.edu.java.parser.handlers.GitHubLinkHandler;
import ru.tinkoff.edu.java.parser.handlers.LinkValidator;
import ru.tinkoff.edu.java.parser.handlers.StackOverflowLinkHandler;

@Configuration
public class LinkHandlersConfig {
    @Bean
    public StackOverflowLinkHandler stackOverflowLinkHandler() {
        return new StackOverflowLinkHandler();
    }

    @Bean
    @Primary
    public GitHubLinkHandler gitHubLinkHandler(StackOverflowLinkHandler stackOverflowLinkHandler) {
        var gitHubLinkHandler = new GitHubLinkHandler();
        gitHubLinkHandler.setNext(stackOverflowLinkHandler);
        return gitHubLinkHandler;
    }

}
