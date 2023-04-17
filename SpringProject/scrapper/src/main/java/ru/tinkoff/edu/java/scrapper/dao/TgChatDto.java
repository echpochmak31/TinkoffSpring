package ru.tinkoff.edu.java.scrapper.dao;

import jakarta.validation.constraints.Min;
import lombok.NonNull;

public record TgChatDto(
        @NonNull
        @Min(0)
        Long chatId,
        @NonNull
        @Min(0)
        Long userId
) {
}
