package ru.tinkoff.edu.java.scrapper.webclients.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hibernate.validator.constraints.URL;

@JsonPropertyOrder({
        "login",
        "id",
        "url"
})
public record GitHubApiOwner(
        @JsonProperty("login") String login,
        @JsonProperty("id") Long id,
        @JsonProperty("url") @URL String url
) {
}
