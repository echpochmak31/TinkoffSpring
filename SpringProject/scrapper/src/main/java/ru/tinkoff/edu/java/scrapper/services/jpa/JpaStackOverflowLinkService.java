package ru.tinkoff.edu.java.scrapper.services.jpa;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.edu.java.scrapper.dao.jpa.JpaStackOverflowLinkRepository;
import ru.tinkoff.edu.java.scrapper.dao.models.StackOverflowLink;
import ru.tinkoff.edu.java.scrapper.services.StackOverflowLinkService;
import java.util.List;

@RequiredArgsConstructor
public class JpaStackOverflowLinkService implements StackOverflowLinkService {
    private final JpaStackOverflowLinkRepository stackOverflowLinkRepository;

    @Override
    public List<StackOverflowLink> getByIds(List<Long> ids) {
        return stackOverflowLinkRepository.findAllById(ids);
    }

    @Override
    public void upsertBatch(List<StackOverflowLink> links) {
        stackOverflowLinkRepository.saveAll(links);
    }
}
