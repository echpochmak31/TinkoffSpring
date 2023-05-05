package ru.tinkoff.edu.java.scrapper.scheduling.apihandlers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.parser.models.UserRepoPair;
import ru.tinkoff.edu.java.parser.results.GitHubParseResult;
import ru.tinkoff.edu.java.parser.results.ParseResult;
import ru.tinkoff.edu.java.scrapper.dao.models.Link;
import ru.tinkoff.edu.java.scrapper.exceptions.UpdatesReceivingException;
import ru.tinkoff.edu.java.scrapper.services.GitHubApiService;
import ru.tinkoff.edu.java.scrapper.webclients.dto.github.GitHubApiEventResponse;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GitHubApiHandler implements ApiHandler {
    private final GitHubApiService gitHubApiService;
    private ApiHandler next;

    @Override
    public ApiHandlerResult handle(@NonNull ParseResult parseResult, @NonNull Link link) {
        var result = new ApiHandlerResult(false, null);

        if (parseResult instanceof GitHubParseResult gitHubParseResult) {

            UserRepoPair userRepoPair = gitHubParseResult.getUserRepoPair();
            GitHubApiEventResponse[] eventList = gitHubApiService
                    .getEvents(userRepoPair.user(), userRepoPair.repository());

            if (eventList == null || eventList.length == 0)
                throw UpdatesReceivingException.gitHubUpdatesFailure(link);

            OffsetDateTime actualLastUpdate = eventList[0].createdAt();

            if (link.getLastUpdate() == null || actualLastUpdate.isAfter(link.getLastUpdate())) {

                result.toBuilder()
                        .hasUpdate(true)
                        .description(generateDescription(eventList, link.getLastUpdate()))
                        .build();

                link.setLastUpdate(actualLastUpdate);

                return result;
            }
            return result;
        } else if (next == null)
            return result;

        return next.handle(parseResult, link);
    }

    @Override
    public void setNext(@NonNull ApiHandler next) {
        this.next = next;
    }

    private String generateDescription(GitHubApiEventResponse[] eventList, OffsetDateTime lastUpdate) {
        var stringBuilder = new StringBuilder();

        Arrays.stream(eventList)
                .filter(x -> x.createdAt().isAfter(lastUpdate))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(x -> x.getValue() == 1)
                .map(Map.Entry::getKey)
                .forEach(x -> stringBuilder.append(x.type()).append("\n"));

        return stringBuilder.toString();
    }
}
