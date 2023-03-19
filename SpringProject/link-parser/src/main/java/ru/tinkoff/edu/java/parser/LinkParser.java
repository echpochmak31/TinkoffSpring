package ru.tinkoff.edu.java.parser;

import ru.tinkoff.edu.java.parser.handlers.LinkHandler;
import ru.tinkoff.edu.java.parser.results.GitHubParseResult;
import ru.tinkoff.edu.java.parser.results.ParseResult;
import ru.tinkoff.edu.java.parser.results.StackOverflowParseResult;

public interface LinkParser {
    ParseResult parseLink(String link);
    void setFirstHandler(LinkHandler linkHandler);
}
