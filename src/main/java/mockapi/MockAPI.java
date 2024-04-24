package mockapi;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public class MockAPI {
    private static final WireMockServer wireMockServer;
    static {
        wireMockServer = new WireMockServer(8080);
    }

    public static void start() {
        wireMockServer.start();
    }

    public static void mockOpenAPI() {
        stubFor(post(urlEqualTo("/sign-document"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBody("true")));
    }
    public static void mockOpenStreetMaps() {
        //TO-DO
    }
    public static void shutdown() {
        wireMockServer.shutdown();
    }
}
