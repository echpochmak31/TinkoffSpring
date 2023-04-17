package ru.tinkoff.edu.java.scrapper.services.jdbc;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dao.JdbcTemplateChatRepository;
import ru.tinkoff.edu.java.scrapper.dao.TgChatDto;
import ru.tinkoff.edu.java.scrapper.services.ChatService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcChatService implements ChatService {

    private final JdbcTemplateChatRepository chatRepository;

    @Override
    public TgChatDto addChat(@Min(0) long tgChatId) {
        return chatRepository.add(tgChatId);
    }

    @Override
    public TgChatDto removeChat(@Min(0) long tgChatId) {
        return chatRepository.remove(tgChatId);
    }

    @Override
    public List<TgChatDto> findAll() {
        return chatRepository.findAll();
    }
}
