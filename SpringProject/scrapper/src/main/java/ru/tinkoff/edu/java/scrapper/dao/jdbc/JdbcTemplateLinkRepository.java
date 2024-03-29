package ru.tinkoff.edu.java.scrapper.dao.jdbc;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.time.Duration;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dao.models.Link;
import ru.tinkoff.edu.java.scrapper.exceptions.ResourceNotFoundException;

@Repository
@RequiredArgsConstructor
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class JdbcTemplateLinkRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public Link add(@Min(0) long tgChatId, @NonNull @NotBlank @URL String link) {

        var linkNamedParams = new MapSqlParameterSource()
            .addValue("url", link);

        String insertLinkSql = "INSERT INTO links.link (url) VALUES (:url) ON CONFLICT DO NOTHING";
        jdbcTemplate.update(insertLinkSql, linkNamedParams);

        String linkSql = "SELECT * FROM links.link WHERE url = :url";
        Link linkModel =
            jdbcTemplate.queryForObject(linkSql, linkNamedParams, DataClassRowMapper.newInstance(Link.class));

        if (linkModel == null) {
            throw ResourceNotFoundException.linkNotFound(tgChatId, link);
        }

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
        var linkModel =
            jdbcTemplate.queryForObject(selectLinkSql, namedParams, DataClassRowMapper.newInstance(Link.class));

        if (linkModel == null) {
            throw ResourceNotFoundException.linkNotFound(tgChatId, link);
        }

        namedParams.addValue("link_id", linkModel.getLinkId());
        String linkChatSql = "DELETE FROM links.link_chat WHERE link_id = :link_id AND chat_id = :chat_id";
        jdbcTemplate.update(linkChatSql, namedParams);

        String linkSql = "DELETE FROM links.link AS L WHERE L.link_id = :link_id AND L.link_id NOT IN "
            + "(SELECT LC.link_id FROM links.link_chat AS LC WHERE LC.link_id = :link_id)";

        jdbcTemplate.update(linkSql, namedParams);

        return linkModel;
    }

    public List<Link> findAll() {
        String sql = "SELECT * FROM links.link";

        return jdbcTemplate.query(sql, DataClassRowMapper.newInstance(Link.class));
    }

    public List<Link> findAllByChatId(@Min(0) long tgChatId) {
        var namedParams = new MapSqlParameterSource()
            .addValue("chat_id", tgChatId);

        String sql = "SELECT * FROM links.link WHERE link_id IN "
            + "(SELECT link_id FROM links.link_chat lc WHERE lc.chat_id = :chat_id)";

        return jdbcTemplate.query(sql, namedParams, DataClassRowMapper.newInstance(Link.class));
    }

    public List<Link> findOldest(@NonNull Duration duration) {
        String sql = "SELECT * FROM links.link WHERE last_check < NOW() - :seconds * INTERVAL '1 second'";

        var namedParams = new MapSqlParameterSource()
            .addValue("seconds", duration.getSeconds());

        return jdbcTemplate.query(sql, namedParams, DataClassRowMapper.newInstance(Link.class));
    }

    public void updateOldest(List<Link> links) {
        String updateSql = "UPDATE links.link SET last_check = NOW() WHERE link_id = :link_id";

        MapSqlParameterSource[] parameterSources = links
            .stream()
            .map(x -> new MapSqlParameterSource().addValue("link_id", x.getLinkId()))
            .toArray(MapSqlParameterSource[]::new);

        jdbcTemplate.batchUpdate(updateSql, parameterSources);
    }

    public void refreshLastUpdate(@NonNull List<Link> linksWithUpdates) {
        String sql = "UPDATE links.link SET last_update = :last_update WHERE link_id = :link_id";

        MapSqlParameterSource[] parameterSources = linksWithUpdates
            .stream()
            .map(x -> new MapSqlParameterSource()
                .addValue("link_id", x.getLinkId())
                .addValue("last_update", x.getLastUpdate()))
            .toArray(MapSqlParameterSource[]::new);

        jdbcTemplate.batchUpdate(sql, parameterSources);
    }

}
