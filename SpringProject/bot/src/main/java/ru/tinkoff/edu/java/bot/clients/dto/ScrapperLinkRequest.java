package ru.tinkoff.edu.java.bot.clients.dto;

import lombok.NonNull;
import org.hibernate.validator.constraints.URL;

public record ScrapperLinkRequest(@NonNull @URL String link) {
}
