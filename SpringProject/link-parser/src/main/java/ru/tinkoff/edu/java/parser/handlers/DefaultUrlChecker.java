package ru.tinkoff.edu.java.parser.handlers;

import org.apache.commons.validator.routines.UrlValidator;
import org.jetbrains.annotations.NotNull;

public class DefaultUrlChecker implements UrlChecker{
    private final UrlValidator urlValidator;
    public DefaultUrlChecker(@NotNull UrlValidator urlValidator) {
        this.urlValidator = urlValidator;
    }
    @Override
    public boolean isValidURL(@NotNull String url) {
        return urlValidator.isValid(url);
    }
}
