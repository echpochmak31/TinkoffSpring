package ru.tinkoff.edu.java.parser.results;

import jakarta.validation.constraints.NotNull;

public abstract class ParseResult {
    protected final String url;

    public ParseResult(@NotNull String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }
}

