package edu.java.configuration;

import edu.java.clients.BotClient;
import edu.java.clients.GithubClient;
import edu.java.clients.StackOverflowClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

@Configuration
public class ClientConfig {

    @Bean
    public GithubClient githubClient(WebClient.Builder webClientBuilder, Retry retry) {
        return new GithubClient(webClientBuilder, retry);
    }

    @Bean
    public StackOverflowClient stackOverflowClient(WebClient.Builder webClientBuilder, Retry retry) {
        return new StackOverflowClient(webClientBuilder, retry);
    }

    @Bean
    public BotClient botClient(WebClient.Builder webClientBuilder, Retry retry) {
        return new BotClient(webClientBuilder, retry);
    }

}
