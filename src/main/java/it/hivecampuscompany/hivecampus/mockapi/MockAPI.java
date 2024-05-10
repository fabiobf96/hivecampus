package it.hivecampuscompany.hivecampus.mockapi;

import com.github.tomakehurst.wiremock.WireMockServer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class MockAPI {
    private static final Logger LOGGER = Logger.getLogger(MockAPI.class.getName());
    private static final WireMockServer wireMockServer = new WireMockServer(8080);
    private MockAPI() {
        throw new IllegalStateException("Utility class");
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

    public static void mockOpenStreetMapAPI() {
        byte[] imageBytes;
        try {
            imageBytes = Files.readAllBytes(Paths.get("src/main/resources/it/hivecampuscompany/hivecampus/images/map.png"));

            stubFor(get(urlEqualTo("/get-map"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "image/png")
                            .withBody(imageBytes)));
        } catch (IOException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    public static void shutdown() {
        wireMockServer.shutdown();
    }
}
