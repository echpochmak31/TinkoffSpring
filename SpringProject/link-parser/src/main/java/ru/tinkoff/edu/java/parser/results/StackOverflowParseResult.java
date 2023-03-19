package ru.tinkoff.edu.java.parser.results;

import org.hibernate.validator.internal.util.stereotypes.Lazy;

import java.util.Optional;

public class StackOverflowParseResult extends ParseResult {
    private int questionId = Integer.MIN_VALUE;
    public StackOverflowParseResult(String url) {
        super(url);
    }

    public int getQuestionId() {
        System.out.println(questionId);
        return questionId;
    }

}
