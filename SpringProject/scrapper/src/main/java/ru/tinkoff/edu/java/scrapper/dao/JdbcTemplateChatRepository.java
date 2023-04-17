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

    public TgChatDto add(long tgChatId) {
        var namedParams = new MapSqlParameterSource()
                .addValue("chat_id", tgChatId);

        jdbcTemplate.update("INSERT INTO links.chat (chat_id) values (:chat_id)", namedParams);

        return new TgChatDto(tgChatId);
    }

    public TgChatDto remove(long tgChatId) {
        var namedParams = new MapSqlParameterSource()
                .addValue("chat_id", tgChatId);

        jdbcTemplate.update("DELETE FROM links.chat WHERE chat_id = :chat_id", namedParams);
        return new TgChatDto(tgChatId);
    }

    public List<TgChatDto> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM links.chat",
                DataClassRowMapper.newInstance(TgChatDto.class)
        );
    }
}
