package ru.tinkoff.edu.java.bot.clients.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.NonNull;

import java.net.URI;

@Valid
public record ScrapperLinkResponse(
        @NonNull @Min(0) Long id,
        @NonNull URI url
) {
}
