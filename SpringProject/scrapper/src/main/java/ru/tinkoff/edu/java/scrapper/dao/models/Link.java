package ru.tinkoff.edu.java.scrapper.dao.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

import java.time.OffsetDateTime;

@Data
@ToString
@AllArgsConstructor
public class Link {
    @NonNull
    @Min(0)
    private Long linkId;

    @NonNull
    @NotBlank
    @URL
    private String url;

    @PastOrPresent
    private OffsetDateTime lastUpdate;

    @PastOrPresent
    private OffsetDateTime lastCheck;
}
