package ru.tinkoff.edu.java.parser.handlers;

import ru.tinkoff.edu.java.parser.results.ParseResult;

public interface LinkHandler {
    void setNext(LinkHandler next);

    ParseResult handle(String link);
}
