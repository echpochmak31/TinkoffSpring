package ru.tinkoff.edu.java.bot.dto;

import lombok.NonNull;

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
