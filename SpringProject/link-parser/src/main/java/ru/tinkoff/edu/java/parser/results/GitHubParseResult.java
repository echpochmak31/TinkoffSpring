package ru.tinkoff.edu.java.parser.results;

import jakarta.validation.constraints.NotNull;

public class GitHubParseResult extends ParseResult {
    public GitHubParseResult(@NotNull String url) {
        super(url);
    }


}
