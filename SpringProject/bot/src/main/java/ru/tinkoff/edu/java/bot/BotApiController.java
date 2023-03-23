package ru.tinkoff.edu.java.bot;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.edu.java.bot.dto.ApiErrorResponse;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateRequest;
import ru.tinkoff.edu.java.bot.dto.LinkUpdateResponse;

@RestController
@RequestMapping("/updates")
public class BotApiController {

    @PostMapping
    public LinkUpdateResponse createUpdate(@Valid @RequestBody LinkUpdateRequest request) {
        return new LinkUpdateResponse();
    }
}
