package ru.tinkoff.edu.java.scrapper.services.jdbc;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dao.JdbcTemplateChatRepository;
import ru.tinkoff.edu.java.scrapper.dao.JdbcTemplateStackOverflowLinkRepository;
import ru.tinkoff.edu.java.scrapper.dao.models.Link;
import ru.tinkoff.edu.java.scrapper.dao.models.StackOverflowLink;
import ru.tinkoff.edu.java.scrapper.services.StackOverflowLinkService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcStackOverflowLinkService implements StackOverflowLinkService {
    private final JdbcTemplateStackOverflowLinkRepository stackOverflowLinkRepository;

    @Override
    public List<StackOverflowLink> getByIds(@NonNull List<Long> ids) {
        return stackOverflowLinkRepository.getByIds(ids);
    }

    @Override
    public void upsertBatch(List<StackOverflowLink> links) {
        stackOverflowLinkRepository.upsertBatch(links);
    }
}
