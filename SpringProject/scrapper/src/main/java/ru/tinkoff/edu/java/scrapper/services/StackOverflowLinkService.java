package ru.tinkoff.edu.java.scrapper.services;

import ru.tinkoff.edu.java.scrapper.dao.models.StackOverflowLink;
import java.util.List;

public interface StackOverflowLinkService {
    List<StackOverflowLink> getByIds(List<Long> ids);

    void upsertBatch(List<StackOverflowLink> links);
}
