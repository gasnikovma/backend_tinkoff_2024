package edu.java.clients;

import edu.java.models.StackOverflowResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class StackOverflowClient {
    private final WebClient webClient;
    private final static String BASE_URL = "https://api.stackexchange.com";

    public StackOverflowClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
    }

    public StackOverflowClient(WebClient.Builder webClientBuilder, String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<StackOverflowResponse> receiveRepo(Long ids) {
        return this.webClient.get().uri("/2.3/questions/{ids}?order=desc&sort=activity&site=stackoverflow", ids)
            .retrieve().bodyToMono(StackOverflowResponse.class);

    }
}
