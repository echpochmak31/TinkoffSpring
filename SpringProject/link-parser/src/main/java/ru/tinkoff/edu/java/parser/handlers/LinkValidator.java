package ru.tinkoff.edu.java.parser.handlers;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import ru.tinkoff.edu.java.parser.results.ParseResult;

import java.lang.reflect.InvocationTargetException;

public class LinkValidator implements Handler {
    @Getter
    @Setter
    private UrlChecker urlChecker;
    private Handler nextHandler;

    public LinkValidator(@NotNull UrlChecker urlChecker) {
        this.urlChecker = urlChecker;
        this.nextHandler = null;
    }

    @Override
    public void setNext(@NotNull Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public ParseResult handle(@NotNull String link) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        if (!urlChecker.isValidURL(link))
            throw new RuntimeException();
        if (nextHandler != null)
            return nextHandler.handle(link);
        return null;
    }

}
