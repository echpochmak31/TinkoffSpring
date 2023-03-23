package ru.tinkoff.edu.java.scrapper.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.exceptions.ResourceNotFoundException;

@RestController
@RequestMapping("/links")
public class LinksController {
    @PostMapping
    public LinkResponse addLink(
            @RequestHeader("Tg-Chat-Id") long tgChatId,
            @Validated @RequestBody AddLinkRequest request) {
        if (!chatExists(tgChatId))
            throw new ResourceNotFoundException("Chat does not not exist: " + tgChatId);

        return new LinkResponse(Long.MAX_VALUE, request.link());
    }

    @GetMapping
    public ListLinkResponse getLinks(@RequestHeader("Tg-Chat-Id") long tgChatId) {
        if (!chatExists(tgChatId))
            throw new ResourceNotFoundException("Chat does not not exist: " + tgChatId);

        LinkResponse[] array = {null, null, null};
        return new ListLinkResponse(array, array.length);
    }

    @DeleteMapping
    public LinkResponse deleteLink(
            @RequestHeader("Tg-Chat-Id") long tgChatId,
            @Validated @RequestBody RemoveLinkRequest request) {

        if (!chatExists(tgChatId))
            throw new ResourceNotFoundException("Chat does not not exist: " + tgChatId);
        return new LinkResponse(0L, request.link());
    }

    private boolean chatExists(long thChatId) {
        return thChatId <= 10;
    }
}
