package ru.tinkoff.edu.java.parser.results;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import ru.tinkoff.edu.java.parser.links.GitHubLink;
import ru.tinkoff.edu.java.parser.links.LinkType;
import ru.tinkoff.edu.java.parser.models.UserRepoPair;

public class GitHubParseResult implements ParseResult {
    private final LinkType linkType = new GitHubLink();
    @Getter
    private final String link;
    public GitHubParseResult(@NotNull String link) {
        this.link = link;
    }

    @Override
    public LinkType getLinkType() {
        return linkType;
    }

}
