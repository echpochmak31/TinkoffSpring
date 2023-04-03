package ru.tinkoff.edu.java.bot.dto;

import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;

public record ApiErrorResponse(
        @NonNull
        String description,
        @NonNull
        String code,
        @NonNull
        String exceptionName,
        @NonNull
        String exceptionMessage,
        @NonNull
        String[] stacktrace
) {

}
