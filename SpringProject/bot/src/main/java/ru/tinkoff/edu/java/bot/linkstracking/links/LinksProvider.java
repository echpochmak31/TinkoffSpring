package ru.tinkoff.edu.java.bot.linkstracking.links;

import java.util.List;

public interface LinksProvider {
    List<String> getLinks(long tgChatId);
}
