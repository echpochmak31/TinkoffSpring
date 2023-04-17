package ru.tinkoff.edu.java.scrapper.services;

import ru.tinkoff.edu.java.scrapper.dao.models.TgChat;

import java.util.List;

public interface ChatService {
    TgChat addChat(long tgChatId);
    TgChat removeChat(long tgChatId);
    List<TgChat> findAll();
}
