package ru.tinkoff.edu.java.parser.models;

import lombok.NonNull;

public record UserRepoPair(@NonNull String user, @NonNull String repository) {
}
