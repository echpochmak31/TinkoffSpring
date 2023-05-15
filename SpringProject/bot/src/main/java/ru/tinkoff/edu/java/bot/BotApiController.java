package ru.tinkoff.edu.java.bot;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateResponse;
import ru.tinkoff.edu.java.bot.services.LinkUpdateMessageHandler;

@RestController
@RequestMapping("/updates")
@RequiredArgsConstructor
public class BotApiController {
    private final LinkUpdateMessageHandler messageHandler;

    @PostMapping
    public LinkUpdateResponse createUpdate(@Valid @RequestBody LinkUpdateRequest request) {
        messageHandler.handle(request.url(), request.description(), request.tgChatsIds());
        return new LinkUpdateResponse();
    }

}
