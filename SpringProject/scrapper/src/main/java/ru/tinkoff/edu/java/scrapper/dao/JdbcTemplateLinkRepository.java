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

import java.time.OffsetDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcTemplateLinkRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Link add(@Min(0) long tgChatId, @NonNull @NotBlank @URL String link) {

        var linkNamedParams = new MapSqlParameterSource()
                .addValue("url", link);

        String insertLinkSql = "INSERT INTO links.link (url) VALUES (:url) ON CONFLICT DO NOTHING";
        jdbcTemplate.update(insertLinkSql, linkNamedParams);

        String linkSql = "SELECT * FROM links.link WHERE url = :url";
        Link linkModel = jdbcTemplate.queryForObject(linkSql, linkNamedParams, DataClassRowMapper.newInstance(Link.class));

        if (linkModel == null)
            throw ResourceNotFoundException.linkNotFound(tgChatId, link);

        var namedParams = new MapSqlParameterSource()
                .addValue("link_id", linkModel.getLinkId())
                .addValue("chat_id", tgChatId);

        String linkChatSql = "INSERT INTO links.link_chat (link_id, chat_id) VALUES (:link_id, :chat_id)";
        jdbcTemplate.update(linkChatSql, namedParams);

        return linkModel;
    }

    public Link remove(@Min(0) long tgChatId, @NonNull @NotBlank @URL String link) {
        var namedParams = new MapSqlParameterSource()
                .addValue("chat_id", tgChatId)
                .addValue("url", link);

        String selectLinkSql = "SELECT * FROM links.link WHERE url = :url";
        var linkModel = jdbcTemplate.queryForObject(selectLinkSql, namedParams, DataClassRowMapper.newInstance(Link.class));

        if (linkModel == null)
            throw ResourceNotFoundException.linkNotFound(tgChatId, link);

        namedParams.addValue("link_id", linkModel.getLinkId());
        String linkChatSql = "DELETE FROM links.link_chat WHERE link_id = :link_id AND chat_id = :chat_id";
        jdbcTemplate.update(linkChatSql, namedParams);

        String linkSql = "DELETE FROM links.link AS L WHERE L.link_id = :link_id AND L.link_id NOT IN " +
                "(SELECT LC.link_id FROM links.link_chat AS LC WHERE LC.link_id = :link_id)";

        jdbcTemplate.update(linkSql, namedParams);

        return linkModel;
    }

    public List<Link> findAll() {
        String sql = "SELECT * FROM links.link";

        return jdbcTemplate.query(sql, DataClassRowMapper.newInstance(Link.class));
    }

}
