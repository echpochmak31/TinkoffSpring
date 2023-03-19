package ru.tinkoff.edu.java.parser.exceptions;

import lombok.NonNull;

public final class UrlParseException extends LinkParserException {
    private UrlParseException(String message) {
        super(message);
    }

    public static UrlParseException parseFailure(@NonNull String targetHostName, @NonNull String url) {
        return new UrlParseException("URL parse failure for " + targetHostName + " triggered by link " + url);
    }
}
