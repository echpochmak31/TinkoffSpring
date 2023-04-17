package ru.tinkoff.edu.java.scrapper.services;

import ru.tinkoff.edu.java.scrapper.dao.LinkDto;

import java.util.List;

public interface LinkService {
    LinkDto addLink(long tgChatId, String link);
    LinkDto removeLink(long tgChatId, String link);
    List<LinkDto> findAll(long tgChatId);
}
