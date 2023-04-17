package ru.tinkoff.edu.java.scrapper.dao;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dao.models.Link;
import ru.tinkoff.edu.java.scrapper.exceptions.ResourceNotFoundException;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcTemplateLinkRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Link add(@Min(0) long tgChatId, @NonNull @NotBlank @URL String link) {

        var linkNamedParams = new MapSqlParameterSource()
                .addValue("url", link);

        String linkSql = "INSERT INTO links.link (url) VALUES (:url) ON CONFLICT DO NOTHING";
        jdbcTemplate.update(linkSql, linkNamedParams);

        String linkIdSql = "SELECT link_id FROM links.link WHERE url = :url";
        var keyHolder = new GeneratedKeyHolder();
        Long linkId = jdbcTemplate.queryForObject(linkIdSql, linkNamedParams, Long.class);

        if (linkId == null)
            throw ResourceNotFoundException.linkNotFound(tgChatId, link);

        var namedParams = new MapSqlParameterSource()
                .addValue("link_id", linkId)
                .addValue("chat_id", tgChatId);

        String linkChatSql = "INSERT INTO links.link_chat (link_id, chat_id) VALUES (:link_id, :chat_id)";
        jdbcTemplate.update(linkChatSql, namedParams);

        return new Link(linkId, tgChatId, link);
    }

    public Link remove(@Min(0) long tgChatId, @NonNull @NotBlank @URL String link) {
        var namedParams = new MapSqlParameterSource()
                .addValue("chat_id", tgChatId)
                .addValue("url", link);

        var keyHolder = new GeneratedKeyHolder();
        String linkChatSql = "DELETE FROM links.link_chat WHERE link_id IN " +
                "(SELECT link_id FROM links.link WHERE url = :url) RETURNING link_id";
        jdbcTemplate.update(linkChatSql, namedParams, keyHolder);

        if (keyHolder.getKey() == null)
            throw ResourceNotFoundException.linkNotFound(tgChatId, link);

        long linkId = keyHolder.getKey().longValue();
        namedParams.addValue("link_id", linkId);

        String linkSql = "DELETE FROM links.link AS L WHERE L.link_id = :link_id AND L.link_id NOT IN " +
                "(SELECT LC.link_id FROM links.link_chat AS LC WHERE LC.link_id = :link_id)";

        jdbcTemplate.update(linkSql, namedParams);

        return new Link(linkId, tgChatId, link);
    }

    public List<Link> findAll(@Min(0) long tgChatId) {
        var linkNamedParams = new MapSqlParameterSource()
                .addValue("chat_id", tgChatId);

        String sql = "SELECT LC.link_id, LC.chat_id, L.url " +
                "FROM links.link AS L " +
                "JOIN links.link_chat AS LC " +
                "ON L.link_id = LC.link_id AND LC.chat_id = :chat_id";

        return jdbcTemplate.query(sql, linkNamedParams, DataClassRowMapper.newInstance(Link.class));
    }

}
