package edu.java.clients;

import edu.java.exceptions.ServiceException;
import edu.java.models.GithubResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

public class GithubClient {
    private final WebClient webClient;

    private final Retry retry;
    private final static String BASE_URL = "https://api.github.com";

    public GithubClient(WebClient.Builder webClientBuilder, Retry retry) {
        this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
        this.retry = retry;
    }

    public GithubClient(WebClient.Builder webClientBuilder, String baseUrl, Retry retry) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.retry = retry;
    }

    public Mono<GithubResponse> receiveRepo(String owner, String repo) {
        return this.webClient
            .get()
            .uri("/repos/{owner}/{repo}", owner, repo)
            .retrieve()
            .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new ServiceException(
                "Server exception", response.statusCode().value())))
            .bodyToMono(GithubResponse.class)
            .retryWhen(retry);

    }
}
