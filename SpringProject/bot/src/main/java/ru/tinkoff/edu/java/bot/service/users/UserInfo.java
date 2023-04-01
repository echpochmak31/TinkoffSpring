package ru.tinkoff.edu.java.bot.service.users;

import lombok.NonNull;

public record UserInfo(@NonNull Long id, @NonNull String username) {
}
