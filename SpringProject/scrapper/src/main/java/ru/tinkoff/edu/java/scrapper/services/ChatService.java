package ru.tinkoff.edu.java.scrapper.services;

import ru.tinkoff.edu.java.scrapper.dao.TgChatDto;

import java.util.List;

public interface ChatService {
    TgChatDto addChat(long tgChatId);
    TgChatDto removeChat(long tgChatId);
    List<TgChatDto> findAll();
}
