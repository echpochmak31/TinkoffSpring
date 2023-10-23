package ru.tinkoff.edu.java.scrapper.scheduling.apihandlers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.tinkoff.edu.java.scrapper.dao.models.StackOverflowLink;
import ru.tinkoff.edu.java.scrapper.services.GitHubApiService;
import ru.tinkoff.edu.java.scrapper.services.StackOverflowApiService;
import java.util.HashMap;

@Configuration
public class ApiHandlersConfig {
    @Bean
    public HashMap<Long, StackOverflowLink> stackOverflowLinkCache() {
        return new HashMap<>();
    }

    @Bean
    @Primary
    public GitHubApiHandler gitHubApiHandler(
        GitHubApiService gitHubApiService,
        StackOverflowApiHandler stackOverflowApiHandler
    ) {
        var gitHubApiHandler = new GitHubApiHandler(gitHubApiService);
        gitHubApiHandler.setNext(stackOverflowApiHandler);
        return gitHubApiHandler;
    }

    @Bean
    public StackOverflowApiHandler stackOverflowApiHandler(
        StackOverflowApiService stackOverflowApiService,
        HashMap<Long, StackOverflowLink> stackOverflowLinkCache
    ) {
        return new StackOverflowApiHandler(stackOverflowApiService, stackOverflowLinkCache);
    }

}
