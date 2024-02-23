package edu.java.clients;

import edu.java.models.GithubResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class GithubClient {
    private final WebClient webClient;
<<<<<<< HEAD
    private final static String BASE_URL = "https://api.github.com";

    public GithubClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
    }

    public GithubClient(WebClient.Builder webClientBuilder, String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public Mono<GithubResponse> receiveRepo(String owner, String repo) {
        return this.webClient.get().uri("/repos/{owner}/{repo}", owner, repo).retrieve()
            .bodyToMono(GithubResponse.class);
=======
    private final String BASE_URL = "https://api.github.com";
    public GithubClient(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl(BASE_URL).build();
    }
    public GithubClient(WebClient.Builder webClientBuilder, String baseUrl){
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }
    public Mono<GithubResponse> receiveRepo(String owner,String repo){
        return this.webClient.get().uri("/repos/{owner}/{repo}",owner,repo).retrieve().bodyToMono(GithubResponse.class);
>>>>>>> 8a3421a (hw2_first)

    }
}
