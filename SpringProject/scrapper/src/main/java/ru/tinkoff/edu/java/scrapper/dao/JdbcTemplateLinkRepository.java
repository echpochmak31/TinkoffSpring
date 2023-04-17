package ru.tinkoff.edu.java.scrapper.dao;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dao.models.Link;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcTemplateLinkRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Link add(@Min(0) long tgChatId, @NonNull @NotBlank @URL String link) {

        var linkNamedParams = new MapSqlParameterSource()
                .addValue("chat_id", tgChatId)
                .addValue("url", link);

        jdbcTemplate.update(
                "INSERT INTO links.link (chat_id, url) values (:chat_id, :url) ON CONFLICT DO NOTHING",
                linkNamedParams);

        return new Link(tgChatId, link);
    }

    public Link remove(@Min(0) long tgChatId, @NonNull @NotBlank @URL String link) {
        var namedParams = new MapSqlParameterSource()
                .addValue("chat_id", tgChatId)
                .addValue("url", link);

        jdbcTemplate.update(
                "DELETE FROM links.link WHERE chat_id = :chat_id AND url = :url",
                namedParams);

        return new Link(tgChatId, link);
    }

    public List<Link> findAll(@Min(0) long tgChatId) {
        var linkNamedParams = new MapSqlParameterSource()
                .addValue("chat_id", tgChatId);

        return jdbcTemplate.query(
                "SELECT * FROM links.link WHERE chat_id = :chat_id",
                linkNamedParams,
                DataClassRowMapper.newInstance(Link.class)
        );
    }

}
