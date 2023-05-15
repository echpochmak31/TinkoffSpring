package ru.tinkoff.edu.java.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcTemplateChatRepository;
import ru.tinkoff.edu.java.scrapper.dao.jdbc.JdbcTemplateLinkRepository;
import ru.tinkoff.edu.java.scrapper.dao.models.Link;
import ru.tinkoff.edu.java.scrapper.dao.models.TgChat;
import javax.sql.DataSource;

@SpringBootTest
@ContextConfiguration(classes = {
    IntegrationEnvironment.IntegrationEnvironmentConfig.class,
    JdbcLinkTest.JdbcConfig.class
})
@Transactional("dataSourceTransactionManager")
@SuppressWarnings({"checkstyle:MethodName", "checkstyle:MultipleStringLiterals"})
public class JdbcLinkTest extends IntegrationEnvironment {

    @Autowired
    private JdbcTemplateChatRepository chatRepository;
    @Autowired
    private JdbcTemplateLinkRepository linkRepository;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    public void Should_ContainChatAndLink_When_AddChatAndLink() {

        long chatId = 1235553426;
        String link = "https://stackoverflow.com/questions/42609306/";

        chatRepository.add(chatId);
        linkRepository.add(chatId, link);

        var chats = chatRepository.findAll();
        var links = linkRepository.findAll();

        Assertions.assertAll(
            () -> Assertions.assertTrue(chats.stream().map(TgChat::getChatId).toList().contains(chatId)),
            () -> Assertions.assertTrue(links.stream().map(Link::getUrl).toList().contains(link))
        );

    }

    @Test
    @Transactional
    @Rollback
    public void Should_BeEmpty_When_RemoveChatAndLink() {

        long chatId = 1235553426;
        String link = "https://stackoverflow.com/questions/42609306/";

        chatRepository.add(chatId);
        linkRepository.add(chatId, link);
        linkRepository.remove(chatId, link);
        chatRepository.remove(chatId);

        var chats = chatRepository.findAll();
        var links = linkRepository.findAll();

        Assertions.assertAll(
            () -> Assertions.assertTrue(chats.isEmpty()),
            () -> Assertions.assertTrue(links.isEmpty())
        );
    }

    @Configuration
    static class JdbcConfig {
        @Bean
        public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

        @Bean
        public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
            return new NamedParameterJdbcTemplate(dataSource);
        }

        @Bean
        public JdbcTemplateLinkRepository jdbcTemplateLinkRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new JdbcTemplateLinkRepository(jdbcTemplate);
        }

        @Bean
        public JdbcTemplateChatRepository jdbcTemplateChatRepository(NamedParameterJdbcTemplate jdbcTemplate) {
            return new JdbcTemplateChatRepository(jdbcTemplate);
        }
    }

}
