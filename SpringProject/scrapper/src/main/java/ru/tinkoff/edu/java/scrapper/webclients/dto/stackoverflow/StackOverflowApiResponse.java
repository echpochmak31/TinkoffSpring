package ru.tinkoff.edu.java.scrapper.webclients.dto.stackoverflow;

import com.fasterxml.jackson.annotation.*;
import ru.tinkoff.edu.java.scrapper.webclients.dto.stackoverflow.StackOverflowApiItems;

@JsonIgnoreProperties({
        "has_more",
        "quota_max",
        "quota_remaining"})
public record StackOverflowApiResponse(@JsonProperty("items") StackOverflowApiItems[] items) {
}
