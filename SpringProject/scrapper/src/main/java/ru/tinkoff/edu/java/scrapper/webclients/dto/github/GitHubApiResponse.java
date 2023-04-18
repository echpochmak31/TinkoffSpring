package ru.tinkoff.edu.java.scrapper.webclients.dto.github;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hibernate.validator.constraints.URL;

import java.time.OffsetDateTime;

@JsonPropertyOrder({
        "id",
        "name",
        "owner",
        "description",
        "url",
        "pushed_at"
})
public record GitHubApiResponse(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name,
        @JsonProperty("owner") GitHubApiOwner owner,
        @JsonProperty("description") String description,
        @JsonProperty("url") @URL String url,
        @JsonProperty("pushed_at") OffsetDateTime pushedAt
        ) {
}
