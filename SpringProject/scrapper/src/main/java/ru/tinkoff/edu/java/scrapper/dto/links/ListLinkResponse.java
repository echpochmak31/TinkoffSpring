package ru.tinkoff.edu.java.scrapper.dto.links;

import jakarta.validation.constraints.Min;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;
import ru.tinkoff.edu.java.scrapper.dto.links.LinkResponse;

@Validated
public record ListLinkResponse(
        @NonNull
        LinkResponse[] links,
        @NonNull
        @Min(0)
        Integer size
) {
}
