import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dao.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.dao.models.Link;
import ru.tinkoff.edu.java.scrapper.dao.models.TgChat;
import ru.tinkoff.edu.java.scrapper.services.jpa.JpaChatService;
import ru.tinkoff.edu.java.scrapper.services.jpa.JpaLinkService;

import javax.sql.DataSource;

@SpringBootTest(classes = {IntegrationEnvironment.IntegrationEnvironmentConfig.class, JpaLinkTest.JpaConfig.class})
@Transactional("transactionManager")
public class JpaLinkTest extends IntegrationEnvironment {

    @Configuration
    @ComponentScan(basePackages = "ru.tinkoff.edu.java.scrapper")
    static class JpaConfig {

        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            var result = new JpaTransactionManager();
            result.setDataSource(dataSource);
            return result;
        }

    }


    @Autowired
    private JpaChatRepository jpaChatRepository;

    @Autowired
    private JpaChatService chatService;
    @Autowired
    private JpaLinkService linkService;


    @Test
    @Transactional
    @Rollback
    public void Should_ContainChatAndLink_When_AddChatAndLink() {
        Assertions.assertAll(
                () -> Assertions.assertTrue(linkService.findAll().isEmpty()),
                () -> Assertions.assertTrue(chatService.findAll().isEmpty())
        );

        long chatId = 1234;
        TgChat chat = chatService.addChat(chatId);
        Link link = linkService.addLink(chatId, "https://stackoverflow.com/questions/58174319");

        Assertions.assertAll(
                () -> Assertions.assertEquals(chatId, chat.getChatId()),
                () -> Assertions.assertTrue(chatService.findAll().contains(chat)),
                () -> Assertions.assertTrue(linkService.findAll().contains(link))
        );
    }

    @Test
    @Transactional
    @Rollback
    public void Should_BeEmpty_When_RemoveLink() {
        Assertions.assertAll(
                () -> Assertions.assertTrue(linkService.findAll().isEmpty()),
                () -> Assertions.assertTrue(chatService.findAll().isEmpty())
        );

        long chatId = 1234;
        TgChat chat = chatService.addChat(chatId);
        Link link = linkService.addLink(chatId, "https://stackoverflow.com/questions/58174319");

        Assertions.assertAll(
                () -> Assertions.assertEquals(chatId, chat.getChatId()),
                () -> Assertions.assertTrue(chatService.findAll().contains(chat)),
                () -> Assertions.assertTrue(linkService.findAll().contains(link))
        );


        linkService.removeLink(chatId, link.getUrl());

        Assertions.assertAll(
                () -> Assertions.assertTrue(linkService.findAll().isEmpty())
        );

    }

}
