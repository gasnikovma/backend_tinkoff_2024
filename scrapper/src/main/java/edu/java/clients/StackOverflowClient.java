package edu.java.clients;

import edu.java.exceptions.ServiceException;
import edu.java.models.StackOverflowResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

public class StackOverflowClient {
    private final WebClient webClient;
    private final static String BASE_URL = "https://api.stackexchange.com";

    private final Retry retry;

    public StackOverflowClient(WebClient.Builder webClientBuilder, Retry retry) {
        this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
        this.retry = retry;
    }

    public StackOverflowClient(WebClient.Builder webClientBuilder, String baseUrl, Retry retry) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.retry = retry;
    }

    public Mono<StackOverflowResponse> receiveRepo(Long ids) {
        return this.webClient
            .get()
            .uri("/2.3/questions/{ids}?order=desc&sort=activity&site=stackoverflow", ids)
            .retrieve()
            .onStatus(HttpStatusCode::is5xxServerError,
                response -> Mono.error(new ServiceException("Server exception", response.statusCode().value())))
            .bodyToMono(StackOverflowResponse.class)
            .retryWhen(retry);

    }
}
