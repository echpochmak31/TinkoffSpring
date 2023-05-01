package ru.tinkoff.edu.java.bot;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.bot.clients.dto.ScrapperLinkRequest;
import ru.tinkoff.edu.java.bot.clients.dto.ScrapperLinkResponse;
import ru.tinkoff.edu.java.bot.clients.dto.ScrapperListLinkResponse;
import ru.tinkoff.edu.java.bot.clients.dto.ScrapperTgChatResponse;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateResponse;
import ru.tinkoff.edu.java.bot.services.MessageHandler;
import ru.tinkoff.edu.java.bot.services.ScrapperApiService;

@RestController
@RequestMapping("/updates")
@RequiredArgsConstructor
public class BotApiController {
    private final MessageHandler messageHandler;

    @PostMapping
    public LinkUpdateResponse createUpdate(@Valid @RequestBody LinkUpdateRequest request) {
        messageHandler.handle(request.url(), request.description(), request.tgChatsIds());
        return new LinkUpdateResponse();
    }

}
