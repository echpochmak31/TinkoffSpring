import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dao.JdbcTemplateChatRepository;
import ru.tinkoff.edu.java.scrapper.dao.JdbcTemplateLinkRepository;
import ru.tinkoff.edu.java.scrapper.dao.models.Link;
import ru.tinkoff.edu.java.scrapper.dao.models.TgChat;

@SpringBootTest
@ContextConfiguration(classes = IntegrationEnvironment.IntegrationEnvironmentConfig.class)
@Transactional("dataSourceTransactionManager")
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
        var links = linkRepository.findAll(chatId);

        Assertions.assertAll(
                () -> Assertions.assertTrue(chats.stream().map(TgChat::chatId).toList().contains(chatId)),
                () -> Assertions.assertTrue(links.stream().map(Link::url).toList().contains(link))
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
        var links = linkRepository.findAll(chatId);

        Assertions.assertAll(
                () -> Assertions.assertTrue(chats.isEmpty()),
                () -> Assertions.assertTrue(links.isEmpty())
        );
    }

}
