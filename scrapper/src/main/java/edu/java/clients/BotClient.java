package edu.java.clients;

import java.util.List;
import edu.java.models.dto.request.LinkUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

public class BotClient {
    private final WebClient webClient;

    public BotClient(WebClient.Builder webClientBuilder, String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public BotClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8090").build();
    }

    public ResponseEntity<Void> update(long id, String uri, String description, List<Long> tgChatIds) {
        return this.webClient.post()
            .uri("/bot/updates")
            .body(BodyInserters.fromValue(new LinkUpdateRequest(id, uri, description, tgChatIds))).retrieve()
            .toEntity(Void.class)
            .block();

    }

}
