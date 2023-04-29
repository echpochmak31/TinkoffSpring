package ru.tinkoff.edu.java.scrapper.dao.jpa;

import jakarta.validation.constraints.Min;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import ru.tinkoff.edu.java.scrapper.dao.models.Link;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    Optional<Link> findByUrl(@NonNull String url);

    List<Link> findAllByTgChatsChatId(@Min(0) long chatId);

    List<Link> findAllByLastCheckLessThan(OffsetDateTime offsetDateTime);

}
