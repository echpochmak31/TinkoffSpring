package ru.tinkoff.edu.java.bot.clients.dto;

import jakarta.validation.constraints.Min;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record ScrapperListLinkResponse(
        @NonNull
        ScrapperLinkResponse[] links,
        @NonNull
        @Min(0)
        Integer size
) {
}
