package ru.tinkoff.edu.java.scrapper.services.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dao.jpa.JpaChatRepository;
import ru.tinkoff.edu.java.scrapper.dao.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.dao.models.Link;
import ru.tinkoff.edu.java.scrapper.dao.models.TgChat;
import ru.tinkoff.edu.java.scrapper.exceptions.ResourceNotFoundException;
import ru.tinkoff.edu.java.scrapper.services.LinkService;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {

    private OffsetDateTime epoch;
    private final JpaLinkRepository linkRepository;
    private final JpaChatRepository chatRepository;

    @Override
    @Transactional
    public Link addLink(long tgChatId, String link) {

        var optionalChat = chatRepository.findById(tgChatId);
        var optionalLink = linkRepository.findByUrl(link);

        if (optionalChat.isEmpty())
            throw ResourceNotFoundException.chatNotFound(tgChatId);

        TgChat chat = optionalChat.get();

        if (optionalLink.isEmpty()) {
            var temporaryLink = Link.builder()
                    .url(link)
                    .lastCheck(OffsetDateTime.now())
                    .build();

            temporaryLink.addChatIfNotExists(chat);
            return linkRepository.saveAndFlush(temporaryLink);
        }

        Link result = optionalLink.get();
        result.addChatIfNotExists(chat);
        return linkRepository.saveAndFlush(result);
    }

    @Override
    @Transactional
    public Link removeLink(long tgChatId, String link) {
        var optionalChat = chatRepository.findById(tgChatId);
        var optionalLink = linkRepository.findByUrl(link);

        if (optionalChat.isEmpty())
            throw ResourceNotFoundException.chatNotFound(tgChatId);

        if (optionalLink.isEmpty())
            throw ResourceNotFoundException.linkNotFound(tgChatId, link);

        TgChat chat = optionalChat.get();
        Link result = optionalLink.get();

        chat.removeLink(result);
        chatRepository.saveAndFlush(chat);

        if (result.getTgChats().isEmpty())
            linkRepository.delete(result);

        return result;
    }

    @Override
    @Transactional
    public List<Link> findAll() {
        return linkRepository.findAll();
    }

    @Override
    @Transactional
    public List<Link> findAllByChatId(long tgChatId) {
        return linkRepository.findAllByTgChatsChatId(tgChatId);
    }

    @Override
    @Transactional
    public List<Link> findOldest(Duration duration) {
        OffsetDateTime offsetDateTime = OffsetDateTime.now();

        List<Link> result = linkRepository.findAllByLastCheckLessThan(offsetDateTime);

        result.forEach(x -> x.setLastCheck(offsetDateTime));

        linkRepository.saveAll(result);

        return result;
    }

    @Override
    @Transactional
    public void refreshLastUpdate(List<Link> linksWithUpdates) {
        linkRepository.saveAll(linksWithUpdates);
    }

}

