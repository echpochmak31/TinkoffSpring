package ru.tinkoff.edu.java.scrapper.controllers;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.chats.TgChatResponse;
import ru.tinkoff.edu.java.scrapper.exceptions.ResourceNotFoundException;
import ru.tinkoff.edu.java.scrapper.services.ChatService;
import ru.tinkoff.edu.java.scrapper.services.LinkService;

@RestController
@Validated
@RequestMapping("/tg-chat")
@RequiredArgsConstructor
public class TgChatsController {

    private final ChatService chatService;

    @PostMapping("/{id}")
    public TgChatResponse addChat(@PathVariable("id") @Min(0) long tgChatId) {
        chatService.addChat(tgChatId);
        return new TgChatResponse();
    }

    @DeleteMapping("/{id}")
    public TgChatResponse deleteChat(@PathVariable("id") @Min(0) long tgChatId) {
        chatService.removeChat(tgChatId);
        return new TgChatResponse();
    }

}
