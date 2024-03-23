package edu.java.repository.jpa;

import edu.java.models.dto.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaChatRepository extends JpaRepository<Chat, Long> {
    int deleteChatEntityById(long id);

    @Modifying
    @SuppressWarnings("LineLength")
    @Query(
        "DELETE FROM Link l WHERE l.id IN (SELECT cl.id FROM Chat c JOIN c.linkEntities cl WHERE c.id = :chatId) AND NOT EXISTS (SELECT cl2.id FROM Chat c2 JOIN c2.linkEntities cl2 WHERE cl2.id = l.id AND c2.id <> :chatId)")
    void deleteLinksByChatId(Long chatId);
}
