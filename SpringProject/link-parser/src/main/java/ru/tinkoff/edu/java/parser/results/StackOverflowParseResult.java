package ru.tinkoff.edu.java.parser.results;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@ToString
public class StackOverflowParseResult extends ParseResult {
    @Getter
    private final int questionId;
    public StackOverflowParseResult(@NotNull String url, int questionId) {
        super(url);
        this.questionId = questionId;
    }
}
