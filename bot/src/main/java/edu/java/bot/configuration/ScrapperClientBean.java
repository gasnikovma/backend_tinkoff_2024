package edu.java.bot.configuration;

import edu.java.bot.clients.ScrapperClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

@Configuration
public class ScrapperClientBean {
    @Bean
    public ScrapperClient scrapperClient(WebClient.Builder webClientBuilder, Retry retry) {
        return new ScrapperClient(webClientBuilder,retry);
    }


}
