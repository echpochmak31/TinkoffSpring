package ru.tinkoff.edu.java.scrapper.scheduling.apihandlers;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;

@Builder(toBuilder = true)
public record ApiHandlerResult(
        boolean hasUpdate,
        String description
) {
}
