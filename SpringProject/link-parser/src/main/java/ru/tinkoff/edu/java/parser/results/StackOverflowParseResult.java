package ru.tinkoff.edu.java.parser.results;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.internal.util.stereotypes.Lazy;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StackOverflowParseResult extends ParseResult {
    private Integer questionId = null;
    public StackOverflowParseResult(@NotNull String url) {
        super(url);
    }

    public int getQuestionId() {
        if (questionId == null) {
            Optional<Integer> result = parseQuestionId(url);
            questionId = result.
        }
            questionId = parse(super.url);
        return questionId;
    }

    private static int parseQuestionId(String url) {
        Pattern regex = Pattern.compile("\\d+(?<=[^d])(?=/)");
        Matcher regexMatcher = regex.matcher(url);

        if (regexMatcher.find()) {
            return Integer.parseInt(regexMatcher.group());
        }
        throw new RuntimeException();
    }
}
