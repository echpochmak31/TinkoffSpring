package ru.tinkoff.edu.java.scrapper.dao.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
public class Link {
    @NonNull
    @Min(0)
    private Long linkId;

    @NonNull
    @Min(0)
    private Long chatId;

    @NonNull
    @NotBlank
    @URL
    private String url;
}
