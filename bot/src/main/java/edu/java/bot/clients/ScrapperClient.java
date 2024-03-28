package edu.java.bot.clients;

import edu.java.bot.exception.ServiceException;
import edu.java.bot.models.request.AddLinkRequest;
import edu.java.bot.models.request.RemoveLinkRequest;
import edu.java.bot.models.response.LinkResponse;
import edu.java.bot.models.response.ListLinksResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import java.rmi.ServerException;
import java.time.Duration;
import java.util.function.Predicate;

@Slf4j
public class ScrapperClient {
    private final WebClient webClient;
    private final String urlTgChat = "/scrapper/tg-chat/{id}";
    private final String urlLinks = "/scrapper/links";
    private final String headerTgChat = "Tg-Chat-Id";

    private final Retry retry;

    public ScrapperClient(WebClient.Builder webClientBuilder, String baseUrl, Retry retry) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.retry = retry;
    }

    public ScrapperClient(WebClient.Builder webClientBuilder, Retry retry) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
        this.retry = retry;

    }

    public ResponseEntity<Void> registerChat(long id) {

        return this.webClient.post()
            .uri(urlTgChat, id)
            .retrieve()
            .onStatus(
                HttpStatusCode::is5xxServerError,
                response -> Mono.error(new ServiceException("Server error", response.statusCode().value()))
            )
            .toEntity(Void.class)
            .retryWhen(retry)
            .block();

    }

    public ResponseEntity<Void> deleteChat(long id) {
        return this.webClient.delete()
            .uri(urlTgChat)
            .retrieve()
            .onStatus(
                HttpStatusCode::is5xxServerError,
                response -> Mono.error(new ServiceException("Server error", response.statusCode().value()))
            )
            .toEntity(Void.class)
            .retryWhen(retry)
            .block();
    }

    public ResponseEntity<ListLinksResponse> getLinks(long chatId) {
        return this.webClient.get()
            .uri(urlLinks).header(headerTgChat, String.valueOf(chatId))
            .retrieve()
            .onStatus(
                HttpStatusCode::is5xxServerError,
                response -> Mono.error(new ServiceException("Server error", response.statusCode().value()))
            )
            .toEntity(ListLinksResponse.class).
            retryWhen(retry).block();

    }

    public ResponseEntity<LinkResponse> addLink(long chatId, String uri) {
        return this.webClient.post()
            .uri(urlLinks)
            .header(headerTgChat, String.valueOf(chatId))
            .body(BodyInserters.fromValue(new AddLinkRequest(uri)))
            .retrieve()
            .onStatus(
                HttpStatusCode::is5xxServerError,
                response -> Mono.error(new ServiceException("Server error", response.statusCode().value()))
            )
            .toEntity(LinkResponse.class)
            .retryWhen(retry)
            .block();

    }

    public ResponseEntity<LinkResponse> removeLink(long chatId, String uri) {
        return this.webClient.method(HttpMethod.DELETE)
            .uri(urlLinks)
            .header(headerTgChat, String.valueOf(chatId))
            .body(BodyInserters.fromValue(new RemoveLinkRequest(uri)))
            .retrieve()
            .onStatus(
                HttpStatusCode::is5xxServerError,
                response -> Mono.error(new ServiceException("Server error", response.statusCode().value()))
            )
            .toEntity(LinkResponse.class)
            .retryWhen(retry)
            .block();
    }

}
