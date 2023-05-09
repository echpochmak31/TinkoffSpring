package ru.tinkoff.edu.java.scrapper.exceptions;

public class ScrapperException extends RuntimeException {
    protected ScrapperException() {
    }

    protected ScrapperException(String message) {
        super(message);
    }
}
