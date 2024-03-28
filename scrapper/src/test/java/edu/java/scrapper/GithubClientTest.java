package edu.java.scrapper;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.models.GithubResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import edu.java.clients.GithubClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;
import java.time.OffsetDateTime;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GithubClientTest {
    private WireMockServer wireMockServer;
    private GithubClient githubClient;
    @Autowired
    private Retry retry;

    @BeforeEach
    public void setup() {
        wireMockServer = new WireMockServer(options().dynamicPort());
        wireMockServer.start();
        WireMock.configureFor("localhost", wireMockServer.port());
        WebClient.Builder webClientBuilder = WebClient.builder();
        githubClient = new GithubClient(webClientBuilder, wireMockServer.baseUrl(),retry);

    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void receiveRepoTest() {
        String wireMockResponse = """
            {
              "owner": {"login": "gasnikovma"},
              "name": "tinkoff",
              "created_at": "2023-10-04T07:17:26Z",
              "updated_at": "2023-12-08T20:21:55Z",
              "pushed_at": "2023-12-17T20:56:54Z"
            }""";
        wireMockServer.stubFor(get(urlEqualTo("/repos/gasnikovma/tinkoff")).willReturn(aResponse().withHeader(
            HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).withBody(wireMockResponse)));
        GithubResponse githubResponse = githubClient.receiveRepo("gasnikovma", "tinkoff").block();
        assertEquals(githubResponse.ownerResponse().nickname(), "gasnikovma");
        assertEquals(githubResponse.repositoryName(), "tinkoff");
        assertEquals(githubResponse.created(), OffsetDateTime.parse("2023-10-04T07:17:26Z"));
        assertEquals(githubResponse.updated(), OffsetDateTime.parse("2023-12-08T20:21:55Z"));
        assertEquals(githubResponse.pushed(), OffsetDateTime.parse("2023-12-17T20:56:54Z"));

    }

}
