package ru.tinkoff.edu.java.parser;

import lombok.NonNull;
import lombok.Setter;
import ru.tinkoff.edu.java.parser.exceptions.LinkParserException;
import ru.tinkoff.edu.java.parser.exceptions.UrlParseException;
import ru.tinkoff.edu.java.parser.handlers.LinkHandler;
import ru.tinkoff.edu.java.parser.results.ParseResult;

public class DefaultLinkParser implements LinkParser {
    @Setter
    private LinkHandler firstHandler;
    public DefaultLinkParser(@NonNull LinkHandler firstHandler) {
        this.firstHandler = firstHandler;
    }

    @Override
    public ParseResult parseLink(String link) {
        ParseResult parseResult = null;
        try {
            return firstHandler.handle(link);
        }
        catch (UrlParseException exception) {
            return null;
        }
    }
}
