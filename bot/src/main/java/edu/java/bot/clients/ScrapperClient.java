package edu.java.bot.clients;

import edu.java.bot.exceptions.NoChatException;
import edu.java.bot.models.request.AddLinkRequest;
import edu.java.bot.models.request.RemoveLinkRequest;
import edu.java.bot.models.response.ApiErrorResponse;
import edu.java.bot.models.response.LinkResponse;
import edu.java.bot.models.response.ListLinksResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
public class ScrapperClient {
    private final WebClient webClient;
    private final String urlTgChat = "/scrapper/tg-chat/{id}";
    private final String urlLinks = "/scrapper/links";
    private final String headerTgChat = "Tg-Chat-Id";

    public ScrapperClient(WebClient.Builder webClientBuilder, String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    public ScrapperClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    public ResponseEntity<Void> registerChat(long id) {

        return this.webClient.post()
            .uri(urlTgChat, id)
            .retrieve()
            .toEntity(Void.class)
            .block();

    }

    public ResponseEntity<Void> deleteChat(long id) {
        return this.webClient.delete()
            .uri(urlTgChat)
            .retrieve().toEntity(Void.class)
            .block();
    }

    public ResponseEntity<ListLinksResponse> getLinks(long chatId) {
        return this.webClient.get()
            .uri(urlLinks).header(headerTgChat, String.valueOf(chatId))
            .retrieve()
            .toEntity(ListLinksResponse.class).block();

    }

   public ResponseEntity<LinkResponse> addLink(long chatId, String uri) {

        return this.webClient.post()
            .uri(urlLinks)
            .header(headerTgChat, String.valueOf(chatId))
            .body(BodyInserters.fromValue(new AddLinkRequest(uri)))
            .retrieve()
            .toEntity(LinkResponse.class)
            .block();

    }
   /*public Mono<LinkResponse> addLink(long chatId, String uri) {

       return this.webClient.post()
           .uri(urlLinks)
           .header(headerTgChat, String.valueOf(chatId))
           .body(BodyInserters.fromValue(new AddLinkRequest(uri)))
           .retrieve().bodyToMono(LinkResponse.class);

   }*/


       public ResponseEntity<LinkResponse> removeLink(long chatId, String uri) {
        return this.webClient.method(HttpMethod.DELETE)
            .uri(urlLinks)
            .header(headerTgChat, String.valueOf(chatId))
            .body(BodyInserters.fromValue(new RemoveLinkRequest(uri)))
            .retrieve()
            .toEntity(LinkResponse.class)
            .block();
    }

}
