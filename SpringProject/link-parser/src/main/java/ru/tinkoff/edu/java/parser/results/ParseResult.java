package ru.tinkoff.edu.java.parser.results;

import lombok.NonNull;

public abstract class ParseResult {
    protected final String url;

    public ParseResult(@NonNull String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }
}

