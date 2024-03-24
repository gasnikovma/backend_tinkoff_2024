package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.clients.StackOverflowClient;
import edu.java.models.StackOverflowResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;
//@SpringBootTest
public class StackOverflowTest {
    private WireMockServer wireMockServer;
    private StackOverflowClient stackOverflowClient;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer(options().dynamicPort());
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
        WebClient.Builder webClientBuilder = WebClient.builder();
        stackOverflowClient = new StackOverflowClient(webClientBuilder, wireMockServer.baseUrl());

    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void receiveRepoTest() {
        String wireMockResponse = """
            {
                "items": [
                    {
                        "last_activity_date": 1659680117,
                        "creation_date": 1365012645,
                        "last_edit_date": 1589194077,
                        "question_id": 15794821

                    }
                ]
            }""";
        wireMockServer.stubFor(get(urlEqualTo("/2.3/questions/15794821?order=desc&sort=activity&site=stackoverflow")).willReturn(
            aResponse().withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBody(wireMockResponse)));
        StackOverflowResponse githubResponse = stackOverflowClient.receiveRepo(15794821L).block();
        assertEquals(githubResponse.itemsResponses().get(0).questionId(), 15794821L);
        assertEquals(githubResponse.itemsResponses().get(0).creationDate(), OffsetDateTime.of(
            LocalDateTime.of(2013, 4, 3, 18, 10,45),
            ZoneOffset.UTC
        ));
    }

}
