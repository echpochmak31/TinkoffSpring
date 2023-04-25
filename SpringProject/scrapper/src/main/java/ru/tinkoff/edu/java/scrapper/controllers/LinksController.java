package ru.tinkoff.edu.java.scrapper.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.links.AddLinkRequest;
import ru.tinkoff.edu.java.scrapper.dto.links.LinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.links.ListLinkResponse;
import ru.tinkoff.edu.java.scrapper.dto.links.RemoveLinkRequest;
import ru.tinkoff.edu.java.scrapper.exceptions.ResourceNotFoundException;
import ru.tinkoff.edu.java.scrapper.services.LinkService;

import java.util.Arrays;

@RestController
@RequestMapping("/links")
@RequiredArgsConstructor
public class LinksController {

    private final LinkService linkService;
    private final ModelMapper modelMapper;

    @PostMapping
    public LinkResponse addLink(
            @RequestHeader("Tg-Chat-Id") long tgChatId,
            @Validated @RequestBody AddLinkRequest request) {

        var link = linkService.addLink(tgChatId, request.link());
        var linkResponse = modelMapper.map(link, LinkResponse.class);
        linkResponse.setChatId(tgChatId);
        return linkResponse;
    }

    @GetMapping
    public ListLinkResponse getLinks(@RequestHeader("Tg-Chat-Id") long tgChatId) {
        var list = linkService.findAll();
        var array = list
                .stream()
                .map(x -> modelMapper.map(x, LinkResponse.class))
                .toArray(LinkResponse[]::new);

        Arrays.stream(array).forEach(x -> x.setChatId(tgChatId));

        return new ListLinkResponse(array, array.length);
    }

    @DeleteMapping
    public LinkResponse deleteLink(
            @RequestHeader("Tg-Chat-Id") long tgChatId,
            @Validated @RequestBody RemoveLinkRequest request) {

        var link = linkService.removeLink(tgChatId, request.link());
        var linkResponse = modelMapper.map(link, LinkResponse.class);
        linkResponse.setChatId(tgChatId);
        return linkResponse;
    }
}
