package ru.tinkoff.edu.java.bot.linkstracking.users;

import lombok.NonNull;

public record UserInfo(@NonNull Long id, @NonNull String username) {
}
