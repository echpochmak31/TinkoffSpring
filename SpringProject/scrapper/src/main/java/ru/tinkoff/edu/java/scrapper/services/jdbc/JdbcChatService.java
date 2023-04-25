package ru.tinkoff.edu.java.scrapper.services.jdbc;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dao.JdbcTemplateChatRepository;
import ru.tinkoff.edu.java.scrapper.dao.models.TgChat;
import ru.tinkoff.edu.java.scrapper.services.ChatService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcChatService implements ChatService {

    private final JdbcTemplateChatRepository chatRepository;

    @Override
    public TgChat addChat(@Min(0) long tgChatId) {
        return chatRepository.add(tgChatId);
    }

    @Override
    public TgChat removeChat(@Min(0) long tgChatId) {
        return chatRepository.remove(tgChatId);
    }

    @Override
    public List<TgChat> findAll() {
        return chatRepository.findAll();
    }

    @Override
    public List<TgChat> findAllByLinkId(@Min(0) long linkId) {
        return chatRepository.findAllByLinkId(linkId);
    }
}
