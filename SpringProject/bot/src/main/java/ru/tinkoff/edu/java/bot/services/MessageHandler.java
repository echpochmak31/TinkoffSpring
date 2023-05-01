package ru.tinkoff.edu.java.bot.services;

import lombok.NonNull;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateMessage;

public interface MessageHandler {
    void handle(@NonNull String url, @NonNull String description, @NonNull Long[] tgChatIds);
}
