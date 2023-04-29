package ru.tinkoff.edu.java.scrapper.scheduling.apihandlers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.tinkoff.edu.java.parser.results.ParseResult;
import ru.tinkoff.edu.java.parser.results.StackOverflowParseResult;
import ru.tinkoff.edu.java.scrapper.dao.models.Link;
import ru.tinkoff.edu.java.scrapper.dao.models.StackOverflowLink;
import ru.tinkoff.edu.java.scrapper.services.StackOverflowApiService;

import java.util.HashMap;

@RequiredArgsConstructor
public class StackOverflowApiHandler implements ApiHandler {
    private final StackOverflowApiService stackOverflowApiService;
    private final HashMap<Long, StackOverflowLink> cache;
    private ApiHandler next;

    @Override
    public ApiHandlerResult handle(@NonNull ParseResult parseResult, @NonNull Link link) {
        var result = new ApiHandlerResult(false, null);

        if (parseResult instanceof StackOverflowParseResult stackOverflowParseResult) {
            var question = stackOverflowApiService
                    .getQuestion(stackOverflowParseResult.getQuestionId())
                    .items()[0];

            var actualLastUpdate = question.lastActivityDate();

            if (link.getLastUpdate() == null || actualLastUpdate.isAfter(link.getLastUpdate())) {
                var commentsResponse = stackOverflowApiService.getComments(stackOverflowParseResult.getQuestionId());

                var description = new StringBuilder();
                description.append("Есть обновления!\n");

                if (cache.containsKey(link.getLinkId())) {
                    var oldLink = cache.get(link.getLinkId());

                    description.append(!oldLink.getIsAnswered() && question.isAnswered()
                            ? "Вопрос помечен как решенный\n" : "");

                    description.append(!oldLink.getAnswersAmount().equals(question.answerCount())
                            ? "Изменилось количество ответов\n" : "");

                    description.append(!oldLink.getCommentAmount().equals(commentsResponse.comments().length)
                            ? "Изменилось количество комментариев\n" : "");
                }

                var stackOverflowLink = StackOverflowLink.builder()
                        .linkId(link.getLinkId())
                        .answersAmount(question.answerCount())
                        .isAnswered(question.isAnswered())
                        .commentAmount(commentsResponse.comments().length)
                        .build();

                cache.put(link.getLinkId(), stackOverflowLink);

                link.setLastUpdate(actualLastUpdate);
                return new ApiHandlerResult(true, description.toString());
            }
            return result;
        }
        else if (next == null)
            return result;

        return next.handle(parseResult, link);
    }

    @Override
    public void setNext(@NonNull ApiHandler next) {
        this.next = next;
    }

}
