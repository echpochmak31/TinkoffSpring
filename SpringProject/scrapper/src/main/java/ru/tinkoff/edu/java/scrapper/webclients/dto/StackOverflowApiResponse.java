package ru.tinkoff.edu.java.scrapper.webclients.dto;

import com.fasterxml.jackson.annotation.*;

@JsonIgnoreProperties({
        "has_more",
        "quota_max",
        "quota_remaining"})
public record StackOverflowApiResponse(@JsonProperty("items") StackOverflowItems[] items) {
}
