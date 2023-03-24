package ru.tinkoff.edu.java.scrapper.dto.links;

import lombok.NonNull;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;

@Validated
public record RemoveLinkRequest(@NonNull @URL String link) {
}
