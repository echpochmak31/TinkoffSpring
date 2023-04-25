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


    @Test
//    @Transactional
//    @Rollback
    public void test() {
        Assertions.assertAll(
                () -> Assertions.assertNotNull(jpaChatRepository),
                () -> Assertions.assertTrue(jpaChatRepository.findAll().isEmpty())
        );

        TgChat chat = chatService.addChat(1234L);

//        TgChat chat = TgChat.builder()
//                .chatId(1234L)
//                .build();
//        jpaChatRepository.save(chat);

        Assertions.assertAll(
                () -> Assertions.assertEquals(1234L, chat.getChatId()),
                () -> Assertions.assertEquals(1, chatService.findAll().size())
        );
    }
}
