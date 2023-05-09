package ru.tinkoff.edu.java.scrapper.scheduling.apihandlers;

import ru.tinkoff.edu.java.parser.results.ParseResult;
import ru.tinkoff.edu.java.scrapper.dao.models.Link;

public interface ApiHandler {
    ApiHandlerResult handle(ParseResult parseResult, Link link);

    void setNext(ApiHandler next);
}
