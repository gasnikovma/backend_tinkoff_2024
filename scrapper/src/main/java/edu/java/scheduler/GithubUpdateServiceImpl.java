package edu.java.scheduler;

import edu.java.clients.BotClient;
import edu.java.clients.GithubClient;
import edu.java.models.GithubResponse;
import edu.java.models.dto.Link;
import edu.java.repository.LinkRepository;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GithubUpdateServiceImpl implements UpdateService {
    private final BotClient botClient;
    private final LinkRepository linkRepository;
    private final GithubClient githubClient;

    @Override
    public void update(Link link) {
        String[] uri = link.getUri().split("/");

        try {
            GithubResponse githubResponse = githubClient.receiveRepo(uri[uri.length - 2], uri[uri.length - 1]).block();
            linkRepository.updateLastCheck(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC), link.getUri());
            if (OffsetDateTime.MIN.equals(link.getLastUpdate())) {
                log.info("только начали отслеживать");
                linkRepository.updateLastUpdate(githubResponse.updated(), link.getUri());
            } else if (githubResponse.updated().isAfter(link.getLastUpdate())) {
                log.info("запушили ");
                linkRepository.updateLastUpdate(githubResponse.updated(), link.getUri());
                botClient.update(
                    link.getId(),
                    link.getUri(),
                    "New update from website:",
                    linkRepository.findChatsByLink(link.getUri())
                );
                //link.setLastUpdate(githubResponse.updated());

            }

        } catch (Exception e) {

        }

    }

}
