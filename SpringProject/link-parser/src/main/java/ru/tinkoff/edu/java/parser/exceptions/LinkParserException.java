package ru.tinkoff.edu.java.parser.exceptions;

public class LinkParserException extends RuntimeException {
    protected LinkParserException() {
    }

    protected LinkParserException(String message) {
        super(message);
    }

    public static LinkParserException noHandlersAvailable() {
        return new LinkParserException("No handlers available.");
    }

}
