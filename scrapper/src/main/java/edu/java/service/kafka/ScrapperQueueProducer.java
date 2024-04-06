package edu.java.service.kafka;

import edu.java.configuration.ApplicationConfig;
import edu.java.models.dto.request.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScrapperQueueProducer {
    private final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;
    private final ApplicationConfig applicationConfig;

    public void sendMessage(LinkUpdateRequest linkUpdateRequest) {
        try {
            kafkaTemplate.send(applicationConfig.kafka().topic(), linkUpdateRequest);

        } catch (Exception e) {
            log.info("Error during sending message in kafka");
            throw new RuntimeException(e.getMessage());
        }

    }

}
