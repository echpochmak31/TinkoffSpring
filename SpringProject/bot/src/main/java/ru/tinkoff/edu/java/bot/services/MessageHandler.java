package ru.tinkoff.edu.java.bot.services;

import ru.tinkoff.edu.java.bot.dto.LinkUpdateMessage;

public interface MessageHandler {
    void handle(LinkUpdateMessage linkUpdateMessage);
}
