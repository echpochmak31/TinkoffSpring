package ru.tinkoff.edu.java.parser.exceptions;

import lombok.NonNull;

public class LinkParserException extends RuntimeException {
    protected LinkParserException() {
        super();
    }

    protected LinkParserException(String message) {
        super(message);
    }

    public static LinkParserException noHandlersAvailable() {
        return new LinkParserException("No handlers available.");
    }


}
