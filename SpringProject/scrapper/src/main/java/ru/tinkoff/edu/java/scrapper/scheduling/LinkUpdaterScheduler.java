package ru.tinkoff.edu.java.scrapper.scheduling;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableScheduling
public class LinkUpdaterScheduler {

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        log.info("Update!");
    }
}

