package ru.tinkoff.edu.java.scrapper.scheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.tinkoff.edu.java.scrapper.services.ChatService;
import ru.tinkoff.edu.java.scrapper.services.LinkService;

@Component
@Slf4j
@RequiredArgsConstructor
public class LinkUpdaterScheduler {

    private final ChatService chatService;
    private final LinkService linkService;

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void update() {
        log.info("Update started!");

        var links = linkService.findAll();

        log.info("Update finished!");
    }
}

