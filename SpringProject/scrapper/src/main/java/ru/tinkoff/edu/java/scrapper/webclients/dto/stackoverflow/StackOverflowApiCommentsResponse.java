package ru.tinkoff.edu.java.scrapper.webclients.dto.stackoverflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({
        "has_more",
        "quota_max",
        "quota_remaining"})
public record StackOverflowApiCommentsResponse(
        @JsonProperty("items") StackOverflowApiComment[] comments
) {
}
