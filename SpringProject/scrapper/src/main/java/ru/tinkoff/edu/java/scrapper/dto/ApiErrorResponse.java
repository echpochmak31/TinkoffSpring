package ru.tinkoff.edu.java.scrapper.dto;

import lombok.NonNull;
import org.springframework.validation.annotation.Validated;

@Validated
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
