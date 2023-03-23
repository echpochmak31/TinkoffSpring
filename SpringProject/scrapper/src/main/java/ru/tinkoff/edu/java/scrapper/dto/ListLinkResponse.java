package ru.tinkoff.edu.java.scrapper.dto;

import jakarta.validation.constraints.Min;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record ListLinkResponse(
        @NonNull
        LinkResponse[] links,
        @NonNull
        @Min(0)
        Integer size
) {
}
