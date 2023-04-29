package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.dao.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.dao.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.dao.jpa.JpaStackOverflowLinkRepository;
import ru.tinkoff.edu.java.scrapper.services.jpa.JpaChatService;
import ru.tinkoff.edu.java.scrapper.services.jpa.JpaLinkService;
import ru.tinkoff.edu.java.scrapper.services.jpa.JpaStackOverflowLinkService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {

    @Bean
    public JpaChatService jpaChatService(JpaChatRepository chatRepository) {
        return new JpaChatService(chatRepository);
    }

    @Bean
    public JpaLinkService jpaLinkService(
            JpaLinkRepository linkRepository,
            JpaChatRepository chatRepository,
            JpaStackOverflowLinkRepository stackOverflowLinkRepository
    ) {
        return new JpaLinkService(linkRepository, chatRepository, stackOverflowLinkRepository);
    }

    @Bean
    public JpaStackOverflowLinkService jpaStackOverflowLinkService(JpaStackOverflowLinkRepository stackOverflowLinkRepository) {
        return new JpaStackOverflowLinkService(stackOverflowLinkRepository);
    }
}
