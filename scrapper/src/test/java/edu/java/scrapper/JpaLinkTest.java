package edu.java.scrapper;

import edu.java.models.dto.Link;
import edu.java.repository.jpa.DefJpaChatRepository;
import edu.java.repository.jpa.DefJpaLinkRepository;
import edu.java.repository.jpa.JpaChatRepository;
import edu.java.repository.jpa.JpaLinkRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
public class JpaLinkTest extends IntegrationTest {

    private final DefJpaLinkRepository defJpaLinkRepository;

    private final DefJpaChatRepository defJpaChatRepository;

    @Autowired
    public JpaLinkTest(JpaChatRepository jpaChatRepository, JpaLinkRepository jpaLinkRepository) {
        defJpaLinkRepository = new DefJpaLinkRepository(jpaLinkRepository);
        defJpaChatRepository = new DefJpaChatRepository(jpaChatRepository);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("addLink")
    public void addLinkTest() {
        defJpaChatRepository.add(100L);
        defJpaChatRepository.add(200L);
        defJpaLinkRepository.add(100L, "matvey.com");
        defJpaLinkRepository.add(100L, "gasnikov.ru");
        defJpaLinkRepository.add(200L, "aleksey.ru");
        List<Link> links = defJpaLinkRepository.findAll();
        assertThat(links.get(1).getUri()).isEqualTo("gasnikov.ru");
        assertThat(links.get(2).getUri()).isEqualTo("aleksey.ru");

    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("removeLink")
    public void removeAndFindLinkTest() {
        defJpaChatRepository.add(100L);
        defJpaChatRepository.add(200L);
        defJpaLinkRepository.add(100L, "matvey.com");
        defJpaLinkRepository.add(100L, "gasnikov.ru");
        defJpaLinkRepository.add(200L, "matvey.com");
        defJpaLinkRepository.remove(100L, "matvey.com");
        assertTrue(defJpaLinkRepository.findByUri("matvey.com").isPresent());
        assertEquals(defJpaLinkRepository.findLinksByChatId(100L).size(), 1);
        assertTrue(defJpaLinkRepository.findByChatIdAndUrl(100L, "matvey.com").isEmpty());
        defJpaLinkRepository.remove(200L, "matvey.com");
        assertEquals(defJpaLinkRepository.findAll().size(), 1);
        assertTrue(defJpaLinkRepository.findByUri("matvey.com").isEmpty());

    }

}
