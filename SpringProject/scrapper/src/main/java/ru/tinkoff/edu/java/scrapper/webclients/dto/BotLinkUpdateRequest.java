package ru.tinkoff.edu.java.scrapper.webclients.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;
import java.net.URI;

@Validated
public record BotLinkUpdateRequest(
    @NonNull
    @Min(0)
    @Max(Long.MAX_VALUE)
    Long id,
    @NonNull
    URI url,
    @NonNull
    String description,
    Long[] tgChatsIds
) {
}
