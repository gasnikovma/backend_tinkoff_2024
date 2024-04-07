package edu.java.bot.kafka;

import edu.java.bot.UpdatesHandlerService;
import edu.java.bot.models.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class LinkUpdateListener {

    private final UpdatesHandlerService service;
    private final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;

    @KafkaListener(topics = "${app.kafka.topic}",
                   groupId = "${app.kafka.group-id}",
                   containerFactory = "containerFactory")
    public void handleUpdate(@Payload LinkUpdateRequest linkUpdateRequest) {
        try {
            log.info("kafka-work");
            service.handleUpdates(linkUpdateRequest);

        } catch (Exception e) {
            kafkaTemplate.send("newUpdate_dlq",linkUpdateRequest);
            log.error("exception");
        }

    }
}
