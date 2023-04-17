package ru.tinkoff.edu.java.scrapper.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.links.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.links.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.links.ListLinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.links.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.exceptions.ResourceNotFoundException;
import ru.tinkoff.edu.java.scrapper.services.LinkService;

@RestController
@RequestMapping("/links")
@RequiredArgsConstructor
public class LinksController {
    private final LinkService linkService;

    @PostMapping
    public LinkResponse addLink(
            @RequestHeader("Tg-Chat-Id") long tgChatId,
            @Validated @RequestBody AddLinkRequest request) {

        throwIfChatNotFound(tgChatId);
        return new LinkResponse(Long.MAX_VALUE, request.link());
    }

    @GetMapping
    public ListLinkResponse getLinks(@RequestHeader("Tg-Chat-Id") long tgChatId) {

        throwIfChatNotFound(tgChatId);
        LinkResponse[] array = {null, null, null};
        return new ListLinkResponse(array, array.length);
    }

    @DeleteMapping
    public LinkResponse deleteLink(
            @RequestHeader("Tg-Chat-Id") long tgChatId,
            @Validated @RequestBody RemoveLinkRequest request) {

        throwIfChatNotFound(tgChatId);
        return new LinkResponse(0L, request.link());
    }

    private boolean chatExists(long tgChatId) {
        return tgChatId <= 10;
    }

    private void throwIfChatNotFound(long tgChatId) {
        if (!chatExists(tgChatId))
            throw ResourceNotFoundException.chatNotFound(tgChatId);
    }
}
