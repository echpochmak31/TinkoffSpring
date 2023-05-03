package ru.tinkoff.edu.java.scrapper.services;

import ru.tinkoff.edu.java.scrapper.dao.models.Link;
import java.time.Duration;
import java.util.List;

public interface LinkService {
    Link addLink(long tgChatId, String link);

    Link removeLink(long tgChatId, String link);

    List<Link> findAll();

    List<Link> findAllByChatId(long tgChatId);

    List<Link> findOldest(Duration duration);

    void updateOldest(List<Link> links);

    void refreshLastUpdate(List<Link> linksWithUpdates);
}
