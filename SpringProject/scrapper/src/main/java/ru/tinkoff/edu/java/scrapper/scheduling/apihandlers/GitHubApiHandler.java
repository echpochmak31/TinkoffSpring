package ru.tinkoff.edu.java.scrapper.scheduling.apihandlers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.parser.results.GitHubParseResult;
import ru.tinkoff.edu.java.parser.results.ParseResult;
import ru.tinkoff.edu.java.scrapper.dao.models.Link;
import ru.tinkoff.edu.java.scrapper.services.GitHubApiService;

@RequiredArgsConstructor
public class GitHubApiHandler implements ApiHandler {
    private final GitHubApiService gitHubApiService;
    private ApiHandler next;
    @Override
    public boolean hasUpdate(@NonNull ParseResult parseResult, @NonNull Link link) {
        if (parseResult instanceof GitHubParseResult gitHubParseResult) {
            var userRepoPair = gitHubParseResult.getUserRepoPair();
            var response = gitHubApiService.getRepository(userRepoPair.user(), userRepoPair.repository());
            var actualLastUpdate = response.pushedAt();
            if (actualLastUpdate != null && actualLastUpdate.isAfter(link.getLastUpdate())) {
                link.setLastUpdate(actualLastUpdate);
                return true;
            }
            return false;
        }
        else if (next == null)
            return false;

        return next.hasUpdate(parseResult, link);
    }

    @Override
    public void setNext(@NonNull ApiHandler next) {
        this.next = next;
    }

}
