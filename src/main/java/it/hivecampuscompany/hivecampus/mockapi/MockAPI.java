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

    /**
     * Private constructor to prevent instantiation of the utility class.
     *
     * @author Fabio Barchiesi
     */
    private MockAPI() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Starts the WireMock server.
     *
     * @author Fabio Barchiesi
     */
    public static void start() {
        wireMockServer.start();
    }

    /**
     * Mocks the OpenAPI for signing documents.
     * It responds with a successful message when a POST request is made to /sign-document.
     *
     * @author Fabio Barchiesi
     */
    public static void mockOpenAPI() {
        stubFor(post(urlEqualTo("/sign-document"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain")
                        .withBody("true")));
    }


    /**
     * Method to mock the OpenStreetMap API.
     * It returns a map image when a GET request is made to /get-map.
     * The image is read from the resources' folder.
     *
     * @author Marina Sotiropoulos
     */

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

    /**
     * Shuts down the WireMock server.
     *
     * @author Fabio Barchiesi
     */
    public static void shutdown() {
        wireMockServer.shutdown();
    }
}
