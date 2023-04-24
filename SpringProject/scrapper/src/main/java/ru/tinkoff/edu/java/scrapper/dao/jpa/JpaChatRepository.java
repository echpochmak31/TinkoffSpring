package ru.tinkoff.edu.java.scrapper.dao.jpa;

import jakarta.validation.constraints.Min;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dao.models.TgChat;

import java.util.List;

@Repository
public interface JpaChatRepository extends JpaRepository<TgChat, Long> {

//    @Query("SELECT c.chat_id FROM links.chat c JOIN links.link_chat lc ON c.chat_id = lc.chat_id AND lc.link_id = :linkId")
//    List<TgChat> findAllByLinkId(@Param("linkId") long linkId);
    List<TgChat> findAllByLinksLinkId(@Min(0) long linkId);
}
