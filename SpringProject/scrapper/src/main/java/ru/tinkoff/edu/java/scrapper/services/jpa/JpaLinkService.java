package ru.tinkoff.edu.java.scrapper.services.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tinkoff.edu.java.scrapper.dao.jpa.JpaLinkRepository;
import ru.tinkoff.edu.java.scrapper.dao.models.Link;
import ru.tinkoff.edu.java.scrapper.services.LinkService;

import java.time.Duration;
import java.util.List;

/*
@Service
@RequiredArgsConstructor
public class JpaLinkService implements LinkService {

    private final JpaLinkRepository linkRepository;

    @Override
    @Transactional
    public Link addLink(long tgChatId, String link) {
        linkRepository.
    }

    @Override
    public Link removeLink(long tgChatId, String link) {

    }

    @Override
    public List<Link> findAll() {
        return null;
    }

    @Override
    public List<Link> findOldest(Duration duration) {
        return null;
    }

    @Override
    public void refreshLastUpdate(List<Link> linksWithUpdates) {

    }
}


 */