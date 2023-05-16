package ru.tinkoff.edu.java.scrapper.dao.jdbc;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.tinkoff.edu.java.scrapper.dao.models.StackOverflowLink;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcTemplateStackOverflowLinkRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public List<StackOverflowLink> getByIds(@NonNull List<Long> ids) {
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }

        MapSqlParameterSource parameters = new MapSqlParameterSource("ids", ids);

        String sql = "SELECT * FROM links.stackoverflow_link WHERE link_id IN (:ids)";

        return jdbcTemplate.query(sql, parameters, DataClassRowMapper.newInstance(StackOverflowLink.class));
    }

    public void upsertBatch(List<StackOverflowLink> links) {
        if (links.isEmpty()) {
            return;
        }

        String sql = "INSERT INTO links.stackoverflow_link "
            + "VALUES(:link_id, :comment_amount, :answers_amount, :is_answered) "
            + "ON CONFLICT (link_id) DO UPDATE SET link_id = :link_id, "
            + "comment_amount = :comment_amount, answers_amount = :answers_amount, is_answered = :is_answered";

        MapSqlParameterSource[] parameterSources = links
            .stream()
            .map(x -> new MapSqlParameterSource()
                .addValue("link_id", x.getLinkId())
                .addValue("comment_amount", x.getCommentAmount())
                .addValue("answers_amount", x.getAnswersAmount())
                .addValue("is_answered", x.getIsAnswered()))
            .toArray(MapSqlParameterSource[]::new);

        jdbcTemplate.batchUpdate(sql, parameterSources);
    }
}
