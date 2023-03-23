package ru.tinkoff.edu.java.parser.results;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString
public class StackOverflowParseResult extends ParseResult {
    @Getter
    private final int questionId;
    public StackOverflowParseResult(@NonNull String url, int questionId) {
        super(url);
        this.questionId = questionId;
    }
}
