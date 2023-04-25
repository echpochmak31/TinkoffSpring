package ru.tinkoff.edu.java.scrapper.webclients.dto.stackoverflow;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.Map;

//@JsonIgnoreProperties({
//        "owner",
//        "is_answered",
//        "view_count",
//        "accepted_answer_id",
//        "answer_count",
//        "score",
//        "content_license",
//})
@JsonIgnoreProperties(ignoreUnknown = true)
public record StackOverflowApiItems(
        @JsonProperty("tags") String[] tags,
        @JsonProperty("is_answered") boolean isAnswered,
        @JsonProperty("answer_count") Integer answerCount,
        @JsonProperty("last_activity_date") OffsetDateTime lastActivityDate,

        @JsonProperty("creation_date") OffsetDateTime creationDate,

        @JsonProperty("last_edit_date") OffsetDateTime lastEditDate,

        @JsonProperty("question_id") Long questionId,

        @JsonProperty("link") String link,

        @JsonProperty("title") String title,

        @JsonIgnore
        Map<String, Object> additionalProperties
) {
}
