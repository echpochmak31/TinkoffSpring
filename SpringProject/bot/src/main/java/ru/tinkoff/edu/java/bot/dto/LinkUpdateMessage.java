package ru.tinkoff.edu.java.bot.dto;

import lombok.NonNull;

public record LinkUpdateMessage(
        @NonNull Long linkId,
        @NonNull String url,
        @NonNull String description,
        @NonNull Long[] tgChatIds
) {
}
