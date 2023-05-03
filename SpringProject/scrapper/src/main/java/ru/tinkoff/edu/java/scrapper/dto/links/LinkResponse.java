package ru.tinkoff.edu.java.scrapper.dto.links;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@AllArgsConstructor
public class LinkResponse {
    @NonNull
    @Min(0)
    Long chatId;
    @NonNull
    @NotBlank
    @URL
    String url;

    public LinkResponse() {

    }
}
