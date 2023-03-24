package ru.tinkoff.edu.java.scrapper.dto.links;

import jakarta.validation.constraints.Min;
import lombok.NonNull;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;

@Validated
public record LinkResponse(
        @NonNull
        @Min(0)
        Long id,
        @NonNull
        @URL
        String url
) {
}
