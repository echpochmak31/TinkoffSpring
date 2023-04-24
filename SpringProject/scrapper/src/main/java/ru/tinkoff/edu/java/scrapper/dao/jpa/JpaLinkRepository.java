package ru.tinkoff.edu.java.scrapper.dao.jpa;

import jakarta.validation.constraints.Min;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tinkoff.edu.java.scrapper.dao.models.Link;

public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    // после By в Derived Query идет название таблицы или поле при many to many ?
    Link updateByTgChatsChatId(@Min(0) long chatId, String url);
}
