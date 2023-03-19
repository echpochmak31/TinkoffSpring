package ru.tinkoff.edu.java.parser.handlers;

import lombok.NonNull;
import ru.tinkoff.edu.java.parser.exceptions.UrlParseException;
import ru.tinkoff.edu.java.parser.handlers.LinkHandler;
import ru.tinkoff.edu.java.parser.results.ParseResult;
import ru.tinkoff.edu.java.parser.results.StackOverflowParseResult;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StackOverflowLinkHandler implements LinkHandler {
    private final String hostName = "stackoverflow.com";
    private LinkHandler next;

    public StackOverflowLinkHandler() {
        next = null;
    }

    @Override
    public void setNext(@NonNull LinkHandler next) {
        this.next = next;
    }

    @Override
    public ParseResult handle(@NonNull String link) {
        if (matches(link))
            return new StackOverflowParseResult(link, parseQuestionId(link));

        if (next != null)
            return next.handle(link);

        return null;
    }

    private boolean matches(String link) {
        try {
            URL url = new URL(link);
            return url.getHost().equals(hostName);
        } catch (MalformedURLException exception) {
            return false;
        }
    }

    private int parseQuestionId(String link) {
        Pattern regex = Pattern.compile("\\d+(?<=[^d])(?=/)");
        Matcher regexMatcher = regex.matcher(link);

        if (regexMatcher.find()) {
            return Integer.parseInt(regexMatcher.group());
        }

        throw UrlParseException.parseFailure(hostName, link);
    }
}
