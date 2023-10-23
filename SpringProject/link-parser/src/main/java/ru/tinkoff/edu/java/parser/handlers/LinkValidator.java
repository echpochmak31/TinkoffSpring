package ru.tinkoff.edu.java.parser.handlers;

import lombok.NonNull;
import org.apache.commons.validator.routines.UrlValidator;
import ru.tinkoff.edu.java.parser.exceptions.LinkParserException;
import ru.tinkoff.edu.java.parser.results.ParseResult;

public class LinkValidator implements LinkHandler {
    private final UrlValidator urlValidator;
    private LinkHandler next;

    public LinkValidator() {
        urlValidator = new UrlValidator();
        next = null;
    }

    @Override
    public void setNext(@NonNull LinkHandler next) {
        this.next = next;
    }

    @Override
    public ParseResult handle(@NonNull String link) {
        if (urlValidator.isValid(link) && next != null) {
            try {
                return next.handle(link);
            } catch (LinkParserException exception) {
                return null;
            }
        }

        return null;
    }
}
