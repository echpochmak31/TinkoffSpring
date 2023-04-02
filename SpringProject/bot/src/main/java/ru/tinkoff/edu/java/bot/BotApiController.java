package ru.tinkoff.edu.java.bot;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.tinkoff.edu.java.bot.clients.dto.ScrapperLinkRequest;
import ru.tinkoff.edu.java.bot.clients.dto.ScrapperLinkResponse;
import ru.tinkoff.edu.java.bot.clients.dto.ScrapperListLinkResponse;
import ru.tinkoff.edu.java.bot.clients.dto.ScrapperTgChatResponse;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateResponse;
import ru.tinkoff.edu.java.bot.services.ScrapperApiService;

@RestController
@RequestMapping("/updates")
public class BotApiController {
    @PostMapping
    public LinkUpdateResponse createUpdate(@Valid @RequestBody LinkUpdateRequest request) {
        return new LinkUpdateResponse();
    }
}
