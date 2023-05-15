package ru.tinkoff.edu.java.bot.linkstracking.links;

public interface LinksUntracker {
    void untrack(long tgChatId, String link);
}
