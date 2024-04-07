package edu.java.service;

import edu.java.clients.BotClient;
import edu.java.configuration.ApplicationConfig;
import edu.java.models.dto.request.LinkUpdateRequest;
import edu.java.service.kafka.ScrapperQueueProducer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LinkUpdateService {
    private final BotClient botClient;
    private final ScrapperQueueProducer scrapperQueueProducer;
    private final ApplicationConfig applicationConfig;

    public void update(LinkUpdateRequest linkUpdateRequest) {
        if (applicationConfig.useQueue()) {
            scrapperQueueProducer.sendMessage(linkUpdateRequest);
        } else {
            botClient.update(linkUpdateRequest.id(),
                linkUpdateRequest.uri(),
                linkUpdateRequest.description(),
                linkUpdateRequest.tgChatIds()).getBody();
        }
    }

}
