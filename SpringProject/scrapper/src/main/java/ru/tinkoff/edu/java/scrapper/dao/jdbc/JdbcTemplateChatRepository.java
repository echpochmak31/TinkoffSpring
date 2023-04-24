package ru.tinkoff.edu.java.scrapper.dao.jdbc;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dao.models.TgChat;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcTemplateChatRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public TgChat add(@Min(0) long tgChatId) {
        var namedParams = new MapSqlParameterSource()
                .addValue("chat_id", tgChatId);

        jdbcTemplate.update("INSERT INTO links.chat (chat_id) values (:chat_id)", namedParams);

        return TgChat.builder().chatId(tgChatId).build();
    }

    public TgChat remove(@Min(0) long tgChatId) {
        var namedParams = new MapSqlParameterSource()
                .addValue("chat_id", tgChatId);

        jdbcTemplate.update("DELETE FROM links.chat WHERE chat_id = :chat_id", namedParams);
        return TgChat.builder().chatId(tgChatId).build();
    }

    public List<TgChat> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM links.chat",
                DataClassRowMapper.newInstance(TgChat.class)
        );
    }

    public List<TgChat> findAllByLinkId(@Min(0) long linkId) {
        var namedParams = new MapSqlParameterSource()
                .addValue("link_id", linkId);

        String sql = "SELECT chat_id FROM links.link_chat WHERE link_id = :link_id";

        return jdbcTemplate.query(sql, namedParams, DataClassRowMapper.newInstance(TgChat.class));
    }
}
