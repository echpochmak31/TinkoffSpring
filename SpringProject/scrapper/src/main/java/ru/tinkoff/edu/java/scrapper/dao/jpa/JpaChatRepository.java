package ru.tinkoff.edu.java.scrapper.dao.jpa;

import jakarta.validation.constraints.Min;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dao.models.TgChat;
import java.util.List;

@Repository
public interface JpaChatRepository extends JpaRepository<TgChat, Long> {
    List<TgChat> findAllByLinksLinkId(@Min(0) long linkId);
}
