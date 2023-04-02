import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.tinkoff.edu.java.parser.handlers.GitHubLinkHandler;
import ru.tinkoff.edu.java.parser.handlers.LinkValidator;
import ru.tinkoff.edu.java.parser.handlers.StackOverflowLinkHandler;
import ru.tinkoff.edu.java.parser.results.GitHubParseResult;
import ru.tinkoff.edu.java.parser.results.ParseResult;
import ru.tinkoff.edu.java.parser.results.StackOverflowParseResult;

public class LinkParserTests {

    private static LinkValidator linkValidator;
    @BeforeAll
    public static void setup() {
        linkValidator = new LinkValidator();
        var gitHubHandler = new GitHubLinkHandler();
        var stackOverflowHandler = new StackOverflowLinkHandler();

        linkValidator.setNext(gitHubHandler);
        gitHubHandler.setNext(stackOverflowHandler);
    }

    @ParameterizedTest
    @ValueSource (strings = {
            "https://stackoverflow.com/questions/7926891/mock-or-stub-for-chained-call",
            "http://stackoverflow.com/questions/9186604/mockito-exception-when-requires-an-argument-which-has-to-be-a-method-call-on",
            "https://stackoverflow.com/questions/58174319/openapi-custom-generator-with-maven-plugin-fails-with-classnotfoundexception",
    })
    public void stackoverflowValidLinksTest(String link) {

        ParseResult result = linkValidator.handle(link);

        Assertions.assertAll(
                () -> Assertions.assertInstanceOf(StackOverflowParseResult.class, result)
        );
    }

    @ParameterizedTest
    @ValueSource (strings = {
            "https://github.com/pengrad/java-telegram-bot-api#creating-your-bot",
            "http://github.com/mplushnikov/lombok-intellij-plugin",
            "https://github.com/sirupsen/logrus/1823y3289hf8wgf78wgf23gd723gd8g2338r7fg287fg238cb8cb832gd723"
    })
    public void githubValidLinksTest(String link) {

        ParseResult result = linkValidator.handle(link);

        Assertions.assertAll(
                () -> Assertions.assertInstanceOf(GitHubParseResult.class, result)
        );
    }

    @ParameterizedTest
    @ValueSource (strings = {
            "https://www.baeldung.com/webflux-webclient-parameters",
            "https://github.com/",
            "sadugsegwe3gwefg87wt2",
            "http://stackoverflow.com/",
            "github.com/mplushnikov"
    })
    public void invalidLinksTest(String link) {

        ParseResult result = linkValidator.handle(link);

        Assertions.assertAll(
                () -> Assertions.assertNull(result)
        );
    }
}
