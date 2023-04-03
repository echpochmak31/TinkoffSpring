package ru.tinkoff.edu.java.scrapper.webclients.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hibernate.validator.constraints.URL;

@JsonPropertyOrder({
        "id",
        "name",
        "owner",
        "description",
        "url"
})
public record GitHubApiResponse(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name,
        @JsonProperty("owner") GitHubApiOwner owner,
        @JsonProperty("description") String description,
        @JsonProperty("url") @URL String url
) {
}
