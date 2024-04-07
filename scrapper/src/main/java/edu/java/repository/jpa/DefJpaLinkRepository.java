package edu.java.repository.jpa;

import edu.java.models.dto.Link;
import edu.java.repository.LinkRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
public class DefJpaLinkRepository implements LinkRepository {
    private final JpaLinkRepository jpaLinkRepository;

    @Override
    @Transactional
    public Link add(long id, String uri) {
        Optional<Link> link = jpaLinkRepository.findByUri(uri);
        Link link1;
        if (link.isPresent()) {
            link1 = link.get();
        } else {
            link1 = new Link(id, uri, OffsetDateTime.now(), OffsetDateTime.now());
            link1 = jpaLinkRepository.save(link1);
        }
        jpaLinkRepository.saveLinkForChat(id, link1.getId());
        return link1;
    }

    @Override
    @Transactional
    public List<Link> findAll() {
        return jpaLinkRepository.findAll();
    }

    @Override
    @Transactional
    public List<Link> findLinksByChatId(long id) {
        return jpaLinkRepository.findLinksByChatId(id);
    }

    @Override
    @Transactional
    public Link remove(long chatId, String uri) {
        Optional<Link> link = findByChatIdAndUrl(chatId, uri);
        long id = link.get().getId();
        jpaLinkRepository.deleteFromChatLink(chatId, id);
        List<Long> chats = findChatsByLink(uri);
        if (chats.isEmpty()) {
            jpaLinkRepository.deleteLinkById(id);
        }
        return link.get();

    }

    @Override
    @Transactional
    public Optional<Link> findByChatIdAndUrl(long id, String uri) {
        List<Link> links = jpaLinkRepository.findLinkByChatIdAndUri(id, uri);
        if (links.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(links.get(0));
    }

    @Override
    @Transactional
    public List<Long> findChatsByLink(String uri) {
        return jpaLinkRepository.findChatsByLink(uri);
    }

    @Override
    @Transactional
    public Optional<Link> findByUri(String uri) {
        return jpaLinkRepository.findByUri(uri);
    }

    @Override
    @Transactional
    public Optional<Link> findById(long linkId) {
        return jpaLinkRepository.findById(linkId);
    }

    @Override
    @Transactional
    public void updateLastCheck(OffsetDateTime check, String uri) {
        Link link = jpaLinkRepository.findByUri(uri).get();
        link.setLastCheck(check);
        jpaLinkRepository.save(link);

    }

    @Override
    @Transactional
    public void updateLastUpdate(OffsetDateTime check, String uri) {
        Link link = jpaLinkRepository.findByUri(uri).get();
        link.setLastUpdate(check);
        jpaLinkRepository.save(link);

    }

    @Override
    @Transactional
    public List<Link> getOldestLinks(int limit) {
        return jpaLinkRepository.getOldestLinks(limit);
    }
}
