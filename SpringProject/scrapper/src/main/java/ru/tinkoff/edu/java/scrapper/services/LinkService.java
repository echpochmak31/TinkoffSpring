package ru.tinkoff.edu.java.scrapper.services;

import ru.tinkoff.edu.java.scrapper.dao.models.Link;

import java.util.List;

public interface LinkService {
    Link addLink(long tgChatId, String link);
    Link removeLink(long tgChatId, String link);
    List<Link> findAll(long tgChatId);
}
