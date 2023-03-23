package ru.tinkoff.edu.java.scrapper.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.ListLinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.RemoveLinkRequest;

@RestController
@RequestMapping("/links")
public class LinksController {
    @PostMapping
    public LinkResponse addLink(
            @RequestHeader("Tg-Chat-Id") long tgChatId,
            @Validated @RequestBody AddLinkRequest request) {
        return new LinkResponse(Long.MAX_VALUE, request.link());
    }

    @GetMapping
    public ListLinkResponse getLinks(@RequestHeader("Tg-Chat-Id") long tgChatId) {
        LinkResponse[] array = {null, null, null};
        return new ListLinkResponse(array, array.length);
    }

    @DeleteMapping
    public LinkResponse deleteLink(
            @RequestHeader("Tg-Chat-Id") long tgChatId,
            @Validated @RequestBody RemoveLinkRequest request) {
        return new LinkResponse(0L, request.link());
    }
}
