package edu.java.scheduler;

import edu.java.clients.BotClient;
import edu.java.clients.GithubClient;
import edu.java.models.GithubResponse;
import edu.java.models.dto.Link;
import edu.java.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

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
           log.info("kuku");
            linkRepository.updateLastCheck(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC), link.getUri());
            //linkRepository.updateLastCheck(OffsetDateTime.now(ZoneId.of("UTC")), link.getUri());
            log.info(githubResponse.pushed().toString());
            log.info(link.getLastCheck().toString());
            log.info(link.getLastUpdate().toString());
            if (OffsetDateTime.MIN.equals(link.getLastUpdate())) {
                log.info("только начали отслеживать");
                linkRepository.updateLastUpdate(githubResponse.updated(), link.getUri());
            }
            else if (githubResponse.pushed().isAfter(link.getLastCheck())) {
                log.info("запушили ");
                linkRepository.updateLastUpdate(githubResponse.updated(), link.getUri());
                botClient.update(link.getId(),
                    link.getUri(),
                    "New update from website:",
                    linkRepository.findChatsByLink(link.getUri()));

            }

        } catch (Exception e) {

        }

    }

    @Override
    public boolean isCorrectUri(String url) {
        return url.startsWith("https://github.com/");
    }
}
