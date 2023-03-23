package ru.tinkoff.edu.java.scrapper.dto;

import jakarta.validation.Valid;
import lombok.NonNull;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;

@Validated
public record AddLinkRequest(@NonNull @URL String link) {
}
