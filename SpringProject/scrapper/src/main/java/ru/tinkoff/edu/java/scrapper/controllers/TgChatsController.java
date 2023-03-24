package ru.tinkoff.edu.java.scrapper.controllers;

import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.scrapper.dto.chats.TgChatResponse;
import ru.tinkoff.edu.java.scrapper.exceptions.ResourceNotFoundException;

@RestController
@Validated
@RequestMapping("/tg-chat")
public class TgChatsController {
    @PostMapping("/{id}")
    public TgChatResponse addChat(@PathVariable("id") @Min(0) long tgChatId) {
        return new TgChatResponse();
    }

    @DeleteMapping("/{id}")
    public TgChatResponse deleteChat(@PathVariable("id") @Min(0) long tgChatId) {
        throwIfChatNotFound(tgChatId);
        return new TgChatResponse();
    }

    private boolean chatExists(long tgChatId) {
        return tgChatId <= 10;
    }

    private void throwIfChatNotFound(long tgChatId) {
        if (!chatExists(tgChatId))
            throw ResourceNotFoundException.chatNotFound(tgChatId);
    }

}
