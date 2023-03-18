package ru.tinkoff.edu.java.parser.models;

import jakarta.validation.constraints.NotNull;

public record UserRepoPair(@NotNull String user, @NotNull String repository) {
}
