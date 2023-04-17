package ru.tinkoff.edu.java.scrapper.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcTemplateChatRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public TgChatDto add(long tgChatId, long tgUserId) {
        var namedParams = new MapSqlParameterSource()
                .addValue("chat_id", tgChatId)
                .addValue("user_id", tgUserId);

        jdbcTemplate.update("INSERT INTO links.chat (chat_id, user_id) values (:chat_id, :user_id)", namedParams);

        return new TgChatDto(tgChatId, tgUserId);
    }

    public TgChatDto remove(long tgChatId) {
        var namedParams = new MapSqlParameterSource()
                .addValue("chat_id", tgChatId);

        var keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update("DELETE FROM links.chat WHERE chat_id = :chat_id RETURNING user_id", namedParams, keyHolder);
        return new TgChatDto(tgChatId, keyHolder.getKey().longValue());
    }

    public List<TgChatDto> findAll(long tgChatId) {
        var namedParams = new MapSqlParameterSource()
                .addValue("chat_id", tgChatId);

        return jdbcTemplate.query(
                "SELECT * FROM links.chat WHERE chat_id = :chat_id",
                namedParams,
                DataClassRowMapper.newInstance(TgChatDto.class)
        );
    }

    public List<TgChatDto> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM links.chat",
                DataClassRowMapper.newInstance(TgChatDto.class)
        );
    }
}
