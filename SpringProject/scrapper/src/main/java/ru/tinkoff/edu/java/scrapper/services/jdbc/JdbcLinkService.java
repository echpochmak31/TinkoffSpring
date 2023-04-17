package ru.tinkoff.edu.java.scrapper.services.jdbc;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.java.scrapper.dao.JdbcTemplateLinkRepository;
import ru.tinkoff.edu.java.scrapper.dao.LinkDto;
import ru.tinkoff.edu.java.scrapper.services.LinkService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {

    private final JdbcTemplateLinkRepository linkRepository;

    @Override
    public LinkDto addLink(@Min(0) long tgChatId, @NonNull @NotBlank @URL String link) {
        return linkRepository.add(tgChatId, link);
    }

    @Override
    public LinkDto removeLink(@Min(0) long tgChatId, @NonNull @NotBlank @URL String link) {
        return linkRepository.remove(tgChatId, link);
    }

    @Override
    public List<LinkDto> findAll(@Min(0) long tgChatId) {
        return linkRepository.findAll(tgChatId);
    }
}
