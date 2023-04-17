package ru.tinkoff.edu.java.scrapper.dao.models;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
public class TgChat {
    @NonNull
    @Min(0)
    private Long chatId;
}
