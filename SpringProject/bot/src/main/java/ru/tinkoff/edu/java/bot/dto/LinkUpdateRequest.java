package ru.tinkoff.edu.java.bot.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record LinkUpdateRequest(
        @NonNull
        @Min(0)
        @Max(Long.MAX_VALUE)
        Long id,
        @NonNull
        String url,
        @NonNull
        String description,
        Long[] tgChatsIds
) {
}
