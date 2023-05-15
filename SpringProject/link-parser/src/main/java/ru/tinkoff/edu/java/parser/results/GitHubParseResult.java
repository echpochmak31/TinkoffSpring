package ru.tinkoff.edu.java.parser.results;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import ru.tinkoff.edu.java.parser.models.UserRepoPair;

@ToString
public class GitHubParseResult extends ParseResult {
    @Getter
    private final UserRepoPair userRepoPair;

    public GitHubParseResult(@NonNull String url, @NonNull UserRepoPair userRepoPair) {
        super(url);
        this.userRepoPair = userRepoPair;
    }

}
