package ru.tinkoff.edu.java.scrapper.dao.models;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Builder
public class StackOverflowLink {
    @NonNull
    @Min(0)
    private Long linkId;

    @NonNull
    @Min(0)
    private Integer commentAmount;

    @NonNull
    @Min(0)
    private Integer answersAmount;

    @NonNull
    private Boolean isAnswered;
}
