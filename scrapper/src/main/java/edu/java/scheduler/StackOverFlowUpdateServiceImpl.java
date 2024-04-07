package edu.java.scheduler;

import edu.java.clients.StackOverflowClient;
import edu.java.models.StackOverflowResponse;
import edu.java.models.dto.Link;
import edu.java.models.dto.request.LinkUpdateRequest;
import edu.java.repository.LinkRepository;
import edu.java.service.LinkUpdateService;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StackOverFlowUpdateServiceImpl implements UpdateService {
    private final LinkUpdateService linkUpdateService;
    private final LinkRepository linkRepository;
    private final StackOverflowClient stackOverflowClient;

    @Override
    public void update(Link link) {
        String[] uri = link.getUri().split("/");
        try {
            StackOverflowResponse stackOverflowResponse =
                stackOverflowClient.receiveRepo(Long.valueOf(uri[uri.length - 2])).block();

            linkRepository.updateLastCheck(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC), link.getUri());
            log.info(stackOverflowResponse.itemsResponses().get(0).lastActivityDate().toString());
            log.info(link.getLastUpdate().toString());
            if (OffsetDateTime.MIN.equals(link.getLastUpdate())) {
                linkRepository.updateLastUpdate(
                    stackOverflowResponse.itemsResponses().get(0).lastActivityDate(),
                    link.getUri()
                );
            } else if (stackOverflowResponse.itemsResponses().get(0).lastActivityDate().isAfter(link.getLastUpdate())) {

                linkRepository.updateLastUpdate(
                    stackOverflowResponse.itemsResponses().get(0).lastActivityDate(),
                    link.getUri()
                );
                linkUpdateService.update(new LinkUpdateRequest(
                    link.getId(),
                    link.getUri(),
                    "New update from website:",
                    linkRepository.findChatsByLink(link.getUri())
                ));
                /*botClient.update(
                    link.getId(),
                    link.getUri(),
                    "New update from website:",
                    linkRepository.findChatsByLink(link.getUri())
                );*/

            }

        } catch (Exception e) {

        }
    }

}
