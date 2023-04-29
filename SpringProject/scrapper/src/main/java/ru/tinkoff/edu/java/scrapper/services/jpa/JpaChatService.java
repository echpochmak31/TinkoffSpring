package ru.tinkoff.edu.java.scrapper.services.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dao.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.dao.models.TgChat;
import ru.tinkoff.edu.java.scrapper.exceptions.ResourceNotFoundException;
import ru.tinkoff.edu.java.scrapper.services.ChatService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class JpaChatService implements ChatService {

    private final JpaChatRepository chatRepository;

    @Override
    @Transactional
    public TgChat addChat(long tgChatId) {
        return chatRepository.save(TgChat.builder().chatId(tgChatId).build());
    }

    @Override
    @Transactional
    public TgChat removeChat(long tgChatId) {
        Optional<TgChat> result = chatRepository.findById(tgChatId);

        if (result.isEmpty())
            throw ResourceNotFoundException.chatNotFound(tgChatId);

        chatRepository.deleteById(tgChatId);
        return result.get();
    }

    @Override
    @Transactional
    public List<TgChat> findAll() {
        return chatRepository.findAll();
    }

    @Override
    @Transactional
    public List<TgChat> findAllByLinkId(long linkId) {
        return chatRepository.findAllByLinksLinkId(linkId);
    }
}
