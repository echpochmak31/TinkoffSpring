package ru.tinkoff.edu.java.parser.results;

import ru.tinkoff.edu.java.parser.links.LinkType;
import ru.tinkoff.edu.java.parser.links.StackOverflowLink;

public class StackOverflowParseResult implements ParseResult {
    private final LinkType linkType = new StackOverflowLink();
    @Override
    public LinkType getLinkType() {
        return linkType;
    }
}
