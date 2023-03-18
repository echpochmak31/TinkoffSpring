package ru.tinkoff.edu.java.parser.handlers;

import jakarta.validation.constraints.NotNull;
import ru.tinkoff.edu.java.parser.results.ParseResult;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Predicate;

public class LinkHandler<T extends ParseResult> implements Handler {
    private final Predicate<String> patternMatcher;
    private final Constructor<? extends T> ctor;
    private Handler next;

    public LinkHandler(
            @NotNull Predicate<String> patternMatcher,
            @NotNull Class<? extends T> type) throws NoSuchMethodException {
        this.patternMatcher = patternMatcher;
        ctor = type.getConstructor();
        next = null;
    }

    @Override
    public void setNext(@NotNull Handler nextHandler) {
        next = nextHandler;
    }

    @Override
    public ParseResult handle(@NotNull String link) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        if (patternMatcher.test(link))
            return ctor.newInstance();
        if (next != null)
            return next.handle(link);
        return null;
    }

}
