package edu.java.repository.jpa;

import edu.java.models.dto.Link;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JpaLinkRepository extends JpaRepository<Link, Long> {
    Optional<Link> findByUri(String uri);

    @Modifying @Query(value = "insert into chat_link (chat_id, link_id) values (:chatId, :linkId)", nativeQuery = true)
    void saveLinkForChat(@Param("chatId") long chatId, @Param("linkId") long linkId);

    @Query("SELECT l FROM Link l JOIN l.chatEntities c WHERE c.id = :chatId") List<Link> findLinksByChatId(
        @Param("chatId") long id
    );

    @Modifying @Query(value = "DELETE FROM chat_link WHERE chat_id = :chatId AND link_id = :linkId", nativeQuery = true)
    void deleteFromChatLink(@Param("chatId") long chatId, @Param("linkId") long linkId);

    int deleteLinkById(long id);

    @Query("SELECT c.id FROM Chat c JOIN c.linkEntities l WHERE l.uri = :uri") List<Long> findChatsByLink(
        @Param("uri") String uri
    );

    @Query("SELECT l FROM Link l JOIN l.chatEntities c WHERE c.id = :chatId AND l.uri = :linkUri")
    List<Link> findLinkByChatIdAndUri(@Param("chatId") long chatId, @Param("linkUri") String linkUri);

    /* @Modifying
     @Query("SELECT l FROM Link l ORDER BY l.lastCheck")
     List<Link> getOldestLinks(int limit);*/
    List<Link> findTopByNByOrderBOrderByLastCheckAsc(int limit);
}
