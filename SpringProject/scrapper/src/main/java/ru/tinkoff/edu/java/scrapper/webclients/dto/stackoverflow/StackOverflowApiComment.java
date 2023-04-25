package ru.tinkoff.edu.java.scrapper.webclients.dto.stackoverflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StackOverflowApiComment(
        @JsonProperty("creation_date") OffsetDateTime creationDate
) {
}
