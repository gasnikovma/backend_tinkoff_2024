package edu.java.clients;

import edu.java.exceptions.ServiceException;
import edu.java.models.dto.request.LinkUpdateRequest;
import java.util.List;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

public class BotClient {
    private final WebClient webClient;

    private final Retry retry;

    public BotClient(WebClient.Builder webClientBuilder, String baseUrl,Retry retry) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.retry=retry;
    }

    public BotClient(WebClient.Builder webClientBuilder, Retry retry) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8090").build();
        this.retry=retry;
    }

    public ResponseEntity<Void> update(long id, String uri, String description, List<Long> tgChatIds) {
        return this.webClient.post()
            .uri("/bot/updates")
            .body(BodyInserters.fromValue(new LinkUpdateRequest(id, uri, description, tgChatIds)))
            .retrieve()
            .onStatus(HttpStatusCode::is5xxServerError,response-> Mono.error(new ServiceException("Server exception",response.statusCode().value())))
            .toEntity(Void.class)
            .retryWhen(retry)
            .block();

    }

}
