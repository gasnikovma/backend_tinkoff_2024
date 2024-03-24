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
            log.info("update last check");
            log.info(githubResponse.pushed().toString());
            log.info(link.getLastUpdate().toString());
            if (OffsetDateTime.MIN.equals(link.getLastUpdate())) {
                linkRepository.updateLastUpdate(githubResponse.pushed(), link.getUri());
            } else if (githubResponse.pushed().isAfter(link.getLastUpdate())) {
                linkRepository.updateLastUpdate(githubResponse.pushed(), link.getUri());
                botClient.update(
                    link.getId(),
                    link.getUri(),
                    "New update from website:",
                    linkRepository.findChatsByLink(link.getUri())
                );

            }

        } catch (Exception e) {

        }

    }

}
