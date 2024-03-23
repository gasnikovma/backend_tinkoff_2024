package edu.java.jdbc;

import edu.java.models.dto.Link;
import edu.java.repository.LinkRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@SuppressWarnings("MultipleStringLiterals")
@RequiredArgsConstructor
public class JdbcLinkRepository implements LinkRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public Link add(long chatId, String uri) {
        Optional<Link> link = findByUri(uri);
        long id;
        if (link.isEmpty()) {
            jdbcTemplate.update(
                "INSERT INTO link(uri, last_update_at,last_check_at) VALUES(?,?,?)",
                uri,
                OffsetDateTime.MIN,
                OffsetDateTime.MIN
            );
            List<Link> allLinks = findAll();
            id = allLinks.get(allLinks.size() - 1).getId();
        } else {
            id = link.get().getId();
        }
        jdbcTemplate.update("INSERT INTO chat_link(chat_id,link_id) VALUES(?,?)", chatId, id);
        return findById(id).get();
    }

    @Override
    @Transactional
    public List<Link> findAll() {
        return jdbcTemplate.query(
            "SELECT * FROM link",
            (resultSet, row) ->
                new Link(
                    resultSet.getLong("id"),
                    resultSet.getString("uri"),
                    resultSet.getObject(
                        "last_check_at",
                        OffsetDateTime.class
                    ),
                    resultSet.getObject("last_update_at", OffsetDateTime.class)
                )
        );

    }

    @Override
    @Transactional
    public List<Link> findLinksByChatId(long chatId) {
        return jdbcTemplate.query(
            "SELECT * FROM link WHERE id IN" + "(SELECT link_id FROM chat_link WHERE chat_id = (?))",
            (resultSet, row) ->
                new Link(
                    resultSet.getLong("id"),
                    resultSet.getString("uri"),
                    resultSet.getObject(
                        "last_check_at",
                        OffsetDateTime.class
                    ),
                    resultSet.getObject("last_update_at", OffsetDateTime.class)
                ),
            chatId
        );
    }

    @Override
    @Transactional
    public Link remove(long chatId, String uri) {
        Optional<Link> link = findByChatIdAndUrl(chatId, uri);
        long id = link.get().getId();
        jdbcTemplate.update("DELETE FROM chat_link WHERE chat_id = (?) AND link_id = (?)", chatId, id);
        List<Long> chats = findChatsByLink(uri);
        if (chats.isEmpty()) {
            jdbcTemplate.update("DELETE FROM link WHERE id = (?)", id);
        }
        return link.get();

    }

    @Override
    @Transactional
    public Optional<Link> findByChatIdAndUrl(long id, String uri) {
        List<Link> links = jdbcTemplate.query(
            "SELECT DISTINCT * FROM chat_link c JOIN link l ON c.link_id = l.id WHERE c.chat_id = ? AND l.uri = ?",
            (resultSet, row) ->
                new Link(
                    resultSet.getLong("id"),
                    resultSet.getString("uri"),
                    resultSet.getObject(
                        "last_check_at",
                        OffsetDateTime.class
                    ),
                    resultSet.getObject("last_update_at", OffsetDateTime.class)
                ),
            id,
            uri
        );
        if (links.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(links.get(0));

    }

    @Override
    @Transactional
    public List<Long> findChatsByLink(String uri) {
        return jdbcTemplate.query(
            "SELECT DISTINCT c.chat_id FROM chat_link c JOIN link l ON l.id = c.link_id WHERE l.uri = (?)",
            (resultSet, row) -> resultSet.getLong("chat_id"),
            uri
        );

    }

    @Override
    @Transactional
    public Optional<Link> findByUri(String uri) {
        List<Link> links = jdbcTemplate.query(
            "SELECT * FROM link WHERE uri = ?",
            (resultSet, row) ->
                new Link(
                    resultSet.getLong("id"),
                    resultSet.getString("uri"),
                    resultSet.getObject(
                        "last_check_at",
                        OffsetDateTime.class
                    ),
                    resultSet.getObject("last_update_at", OffsetDateTime.class)
                ), uri
        );
        return links.isEmpty() ? Optional.empty() : Optional.of(links.get(0));
    }

    @Override
    @Transactional
    public Optional<Link> findById(long linkId) {
        List<Link> links = jdbcTemplate.query(
            "SELECT * FROM link WHERE id = ?",
            (resultSet, row) ->
                new Link(
                    resultSet.getLong("id"),
                    resultSet.getString("uri"),
                    resultSet.getObject(
                        "last_check_at",
                        OffsetDateTime.class
                    ),
                    resultSet.getObject("last_update_at", OffsetDateTime.class)
                ), linkId
        );
        return links.isEmpty() ? Optional.empty() : Optional.of(links.get(0));
    }

    @Override
    @Transactional
    public void updateLastCheck(OffsetDateTime check, String uri) {
        jdbcTemplate.update("UPDATE link SET last_check_at = (?) WHERE uri = (?)", check, uri);
    }

    @Override
    @Transactional
    public void updateLastUpdate(OffsetDateTime check, String uri) {
        jdbcTemplate.update("UPDATE link SET last_update_at = (?) WHERE uri = (?)", check, uri);
    }

    @Override
    @Transactional
    public List<Link> getOldestLinks(int limit) {
        return jdbcTemplate.query("SELECT * FROM link ORDER BY last_check_at LIMIT (?)", (resultSet, row) -> new Link(
            resultSet.getLong("id"),
            resultSet.getString("uri"),
            resultSet.getObject(
                "last_check_at",
                OffsetDateTime.class
            ),
            resultSet.getObject("last_update_at", OffsetDateTime.class)
        ),limit);
    }
}
