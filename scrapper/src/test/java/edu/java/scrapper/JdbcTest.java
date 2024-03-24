package edu.java.scrapper;

import edu.java.models.dto.Chat;
import edu.java.models.dto.Link;
import edu.java.repository.jdbc.JdbcChatRepository;
import edu.java.repository.jdbc.JdbcLinkRepository;
import edu.java.repository.ChatRepository;
import edu.java.repository.LinkRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest
public class JdbcTest extends IntegrationTest {
    private static final JdbcTemplate jdbcTemplate =
        new JdbcTemplate(DataSourceBuilder.create().url(POSTGRES.getJdbcUrl()).username(POSTGRES.getUsername())
            .password(POSTGRES.getPassword()).build());

    private ChatRepository chatRepository;
    private LinkRepository linkRepository;

    @Autowired
    public JdbcTest(){
        chatRepository = new JdbcChatRepository(jdbcTemplate);
        linkRepository = new JdbcLinkRepository(jdbcTemplate);
    }


    @Test
    @Transactional
    @Rollback
    @DisplayName("addChat")
    public void addChatTest() {
        chatRepository.add(100L);
        chatRepository.add(105L);
        List<Chat> chats = chatRepository.findAll();
        assertThat(100L).isEqualTo(chats.get(0).getId());
        assertThat(105L).isEqualTo(chats.get(1).getId());
        chatRepository.remove(100L);
        chatRepository.remove(105L);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("removeChat")
    public void deleteChatTest() {
        chatRepository.add(500L);
        chatRepository.remove(500L);
        List<Chat> chats = chatRepository.findAll();
        assertThat(chats.isEmpty()).isTrue();

    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("findAllChats")
    public void findAllChatsTest() {
        chatRepository.add(600L);
        chatRepository.add(605L);
        chatRepository.add(610L);
        List<Chat> chats = chatRepository.findAll();
        assertEquals(chats.size(), 3);
        chatRepository.remove(600L);
        chatRepository.remove(605L);
        chatRepository.remove(610L);

    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("findChatById")
    public void findChatByIdTest() {
        chatRepository.add(700L);
        chatRepository.add(705L);
        chatRepository.add(710L);
        Optional<Chat> chat = chatRepository.findById(705L);
        assertTrue(chat.isPresent());
        Optional<Chat> emptyChat = chatRepository.findById(706L);
        assertTrue(emptyChat.isEmpty());
        chatRepository.remove(700L);
        chatRepository.remove(705L);
        chatRepository.remove(710L);

    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("AddLink")
    public void addLinkTest() {
        chatRepository.add(100L);
        chatRepository.add(200L);
        linkRepository.add(100L, "matvey.com");
        linkRepository.add(100L, "gasnikov.ru");
        linkRepository.add(200L, "aleksey.ru");
        List<Link> links = linkRepository.findAll();
        assertThat(links.get(1).getUri()).isEqualTo("gasnikov.ru");
        assertThat(links.get(2).getUri()).isEqualTo("aleksey.ru");
        chatRepository.remove(100L);
        chatRepository.remove(200L);

    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("FindLinksByChatId")
    public void findLinksByChatIdTest() {
        chatRepository.add(100L);
        chatRepository.add(200L);
        linkRepository.add(100L, "matvey.com");
        linkRepository.add(200L, "gasnikov.ru");
        linkRepository.add(100L, "aleksey.ru");
        List<Link> links = linkRepository.findLinksByChatId(100L);
        assertEquals(links.size(), 2);
        assertThat(links.get(0).getUri()).isEqualTo("matvey.com");
        assertThat(links.get(1).getUri()).isEqualTo("aleksey.ru");
        chatRepository.remove(100L);
        chatRepository.remove(200L);

    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("removeLink")
    public void removeLinkTest() {
        chatRepository.add(100L);
        chatRepository.add(200L);
        linkRepository.add(100L, "matvey.com");
        linkRepository.add(200L, "gasnikov.ru");
        linkRepository.add(100L, "aleksey.ru");
        linkRepository.remove(100L, "aleksey.ru");
        List<Link> links = linkRepository.findLinksByChatId(100L);
        assertEquals(links.size(), 1);
        assertThat(links.get(0).getUri()).isEqualTo("matvey.com");
        chatRepository.remove(100L);
        chatRepository.remove(200L);

    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("findLinkByChatIdAndUrl")
    public void findLinkByChatIdAndUrlTest() {
        chatRepository.add(100L);
        chatRepository.add(200L);
        linkRepository.add(100L, "matvey.com");
        linkRepository.add(200L, "gasnikov.ru");
        linkRepository.add(100L, "aleksey.ru");
        linkRepository.remove(100L, "aleksey.ru");
        Optional<Link> link = linkRepository.findByChatIdAndUrl(100L, "matvey.com");
        Optional<Link> emptyLink = linkRepository.findByChatIdAndUrl(100L, "gasnikov.ru");
        assertTrue(link.isPresent());
        assertTrue(emptyLink.isEmpty());
        chatRepository.remove(100L);
        chatRepository.remove(200L);

    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("findByUri")
    public void findByUriTest() {
        chatRepository.add(100L);
        chatRepository.add(200L);
        linkRepository.add(100L, "matvey.com");
        linkRepository.add(200L, "gasnikov.ru");
        linkRepository.add(100L, "aleksey.ru");
        linkRepository.remove(100L, "aleksey.ru");
        Optional<Link> link = linkRepository.findByUri("matvey.com");
        Optional<Link> emptyLink = linkRepository.findByUri("matv.com");
        assertTrue(link.isPresent());
        assertTrue(emptyLink.isEmpty());
        chatRepository.remove(100L);
        chatRepository.remove(200L);

    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("updateLastCheck")
    public void updateLastCheckTest() {
        chatRepository.add(100L);
        chatRepository.add(200L);
        linkRepository.add(100L, "matvey.com");
        linkRepository.add(200L, "gasnikov.ru");
        linkRepository.add(200L, "matvey.com");
        OffsetDateTime dateTime = OffsetDateTime.of(2024, 2, 23, 10, 25, 43, 0, ZoneOffset.UTC);
        linkRepository.updateLastCheck(dateTime, "matvey.com");
        Optional<Link> link = linkRepository.findByChatIdAndUrl(100L, "matvey.com");
        assertTrue(link.isPresent());
        assertEquals(link.get().getLastCheck().toLocalDateTime(), dateTime.toLocalDateTime());
        chatRepository.remove(100L);
        chatRepository.remove(200L);

    }

}
