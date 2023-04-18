package ru.tinkoff.edu.java.scrapper.scheduling.apihandlers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.parser.results.ParseResult;
import ru.tinkoff.edu.java.parser.results.StackOverflowParseResult;
import ru.tinkoff.edu.java.scrapper.dao.models.Link;
import ru.tinkoff.edu.java.scrapper.services.StackOverflowApiService;

@RequiredArgsConstructor
public class StackOverflowApiHandler implements ApiHandler {
    private final StackOverflowApiService stackOverflowApiService;
    private ApiHandler next;

    @Override
    public boolean hasUpdate(@NonNull ParseResult parseResult, @NonNull Link link) {
        if (parseResult instanceof StackOverflowParseResult stackOverflowParseResult) {
            var response = stackOverflowApiService.getQuestion(stackOverflowParseResult.getQuestionId());
            var actualLastUpdate = response.items()[0].lastEditDate();
            if (actualLastUpdate != null && actualLastUpdate.isBefore(link.getLastUpdate())) {
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
