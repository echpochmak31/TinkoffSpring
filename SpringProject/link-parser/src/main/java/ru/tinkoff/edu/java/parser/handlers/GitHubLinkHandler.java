package ru.tinkoff.edu.java.parser.handlers;

import lombok.NonNull;
import ru.tinkoff.edu.java.parser.exceptions.UrlParseException;
import ru.tinkoff.edu.java.parser.models.UserRepoPair;
import ru.tinkoff.edu.java.parser.results.GitHubParseResult;
import ru.tinkoff.edu.java.parser.results.ParseResult;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitHubLinkHandler implements LinkHandler {
    private static final String hostName = "github.com";
    private static final Pattern regex = Pattern.compile("([^/]+)/[^/]+");
    private LinkHandler next;

    public GitHubLinkHandler() {
        next = null;
    }

    @Override
    public void setNext(@NonNull LinkHandler next) {
        this.next = next;
    }

    @Override
    public ParseResult handle(@NonNull String link) {
        if (matches(link)) {
            return new GitHubParseResult(link, parseUserRepoPair(link));
        }

        if (next == null) {
            return null;
        }

        return Optional.of(next).map(x -> next.handle(link)).orElse(null);
    }

    private boolean matches(String link) {
        try {
            URL url = new URL(link);
            return url.getHost().equals(hostName);
        } catch (MalformedURLException exception) {
            return false;
        }
    }

    private UserRepoPair parseUserRepoPair(String link) {
        try {
            URL url = new URL(link);
            Matcher regexMatcher = regex.matcher(url.getPath());

            if (regexMatcher.find()) {
                String[] tokens = regexMatcher.group().split("/");
                var username = tokens[0];
                var repository = tokens[1];
                return new UserRepoPair(username, repository);
            }
            throw UrlParseException.parseFailure(hostName, link);
        } catch (MalformedURLException exception) {
            throw UrlParseException.parseFailure(hostName, link);
        }
    }
}
