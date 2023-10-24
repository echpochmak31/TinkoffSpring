package ru.tinkoff.edu.java.bot.metrics;

import io.micrometer.core.instrument.Metrics;
import jakarta.validation.constraints.Min;

public final class BotMetrics {
    private BotMetrics() {
    }

    public static void incrementUpdatesCounter(@Min(0) int count) {
        Metrics.counter("bot_update_count").increment(count);
    }
}
