package ru.tinkoff.edu.java.scrapper.dto;

import lombok.Builder;
import lombok.NonNull;

@Builder
public record LinkUpdateMessage(
        @NonNull Long linkId,
        @NonNull String url,
        @NonNull String description,
        @NonNull Long[] tgChatIds
) {
}
