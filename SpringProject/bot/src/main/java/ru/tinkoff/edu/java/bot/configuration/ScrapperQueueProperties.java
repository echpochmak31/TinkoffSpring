package ru.tinkoff.edu.java.bot.configuration;

import jakarta.validation.constraints.NotNull;

public record ScrapperQueueProperties(
        @NotNull String queueName,
        @NotNull String exchangeName,
        @NotNull String routingKey
) {
}
