package edu.java.configuration;

import edu.java.clients.GithubClient;
import edu.java.clients.StackOverflowClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    @Bean
<<<<<<< HEAD
    public GithubClient githubClient(WebClient.Builder webClientBuilder) {
=======
    public GithubClient githubClient(WebClient.Builder webClientBuilder){
>>>>>>> 8a3421a (hw2_first)
        return new GithubClient(webClientBuilder);
    }

    @Bean
<<<<<<< HEAD
    public StackOverflowClient stackOverflowClient(WebClient.Builder webClientBuilder) {
=======
    public StackOverflowClient stackOverflowClient(WebClient.Builder webClientBuilder){
>>>>>>> 8a3421a (hw2_first)
        return new StackOverflowClient(webClientBuilder);
    }

}
