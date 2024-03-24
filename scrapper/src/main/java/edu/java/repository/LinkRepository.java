package edu.java.repository;

import edu.java.models.dto.Link;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface LinkRepository {
    Link add(long id, String uri);

    List<Link> findAll();

    List<Link> findLinksByChatId(long id);

    Link remove(long chatId, String uri);

    Optional<Link> findByChatIdAndUrl(long id, String uri);

    List<Long> findChatsByLink(String uri);

    Optional<Link> findByUri(String uri);

    Optional<Link> findById(long linkId);

    void updateLastCheck(OffsetDateTime check, String name);

    void updateLastUpdate(OffsetDateTime check, String name);

    List<Link> getOldestLinks(int limit);

}
