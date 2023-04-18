package ru.tinkoff.edu.java.scrapper.webclients.dto.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GitHubApiEventResponse(
        @JsonProperty("type") String type,
        @JsonProperty("created_at") OffsetDateTime createdAt
) {
}

