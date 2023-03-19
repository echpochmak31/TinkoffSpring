package ru.tinkoff.edu.java.parser.exceptions;

public class UrlParseException extends LinkParserException {
    private UrlParseException(String message) {
        super(message);
    }

    public static UrlParseException parseFailure(String message) {
        return new UrlParseException(message);
    }
}
