package ru.tinkoff.edu.java.scrapper.dao;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.hibernate.validator.constraints.URL;

public record LinkDto(
        @NonNull
        @Min(0)
        Long chatId,
        @NonNull
        @NotBlank
        @URL
        String url
) {
}
