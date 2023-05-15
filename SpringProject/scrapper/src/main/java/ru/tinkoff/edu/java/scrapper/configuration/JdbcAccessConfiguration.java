package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcTemplateChatRepository;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcTemplateLinkRepository;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcTemplateStackOverflowLinkRepository;
import ru.tinkoff.edu.java.scrapper.services.jdbc.JdbcChatService;
import ru.tinkoff.edu.java.scrapper.services.jdbc.JdbcLinkService;
import ru.tinkoff.edu.java.scrapper.services.jdbc.JdbcStackOverflowLinkService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {

    @Bean
    public JdbcChatService jdbcChatService(JdbcTemplateChatRepository chatRepository) {
        return new JdbcChatService(chatRepository);
    }

    @Bean
    public JdbcLinkService jdbcLinkService(JdbcTemplateLinkRepository linkRepository) {
        return new JdbcLinkService(linkRepository);
    }

    @Bean
    public JdbcStackOverflowLinkService jdbcStackOverflowLinkService(
        JdbcTemplateStackOverflowLinkRepository stackOverflowLinkRepository
    ) {
        return new JdbcStackOverflowLinkService(stackOverflowLinkRepository);
    }
}
