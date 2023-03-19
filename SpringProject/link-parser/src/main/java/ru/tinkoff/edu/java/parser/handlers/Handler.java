package ru.tinkoff.edu.java.parser.handlers;

import ru.tinkoff.edu.java.parser.results.ParseResult;

import java.lang.reflect.InvocationTargetException;

public interface Handler {
    void setNext(Handler nextHandler);

    ParseResult handle(String request);

}
