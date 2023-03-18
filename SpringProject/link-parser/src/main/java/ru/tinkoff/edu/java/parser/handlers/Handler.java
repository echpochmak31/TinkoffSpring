package ru.tinkoff.edu.java.parser.handlers;

import ru.tinkoff.edu.java.parser.results.ParseResult;
import ru.tinkoff.edu.java.parser.results.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public interface Handler {
    void setNext(Handler nextHandler);

    ParseResult handle(String request) throws InstantiationException, IllegalAccessException, InvocationTargetException ;

}
