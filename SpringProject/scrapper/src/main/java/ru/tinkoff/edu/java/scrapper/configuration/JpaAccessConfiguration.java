package ru.tinkoff.edu.java.scrapper.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.java.scrapper.dao.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.services.jpa.JpaChatService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {

    @Bean
    public JpaChatService jpaChatService(JpaChatRepository chatRepository) {
        return new JpaChatService(chatRepository);
    }
}
