package it.hivecampuscompany.hivecampus.boundary;

import mockapi.MockAPI;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.logging.Logger;

public class OpenApiBoundary {
    private static final Logger LOGGER = Logger.getLogger(OpenApiBoundary.class.getName());
    public boolean signContract(byte[] contract) {
        MockAPI.mockOpenAPI();
        MockAPI.start();

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/sign-document"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofByteArray(contract))
                    .build();
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            int responseCode = response.statusCode();

            if (responseCode == HttpURLConnection.HTTP_OK && response.body().equals("true")) {
                return true;
            } else {
                LOGGER.warning("POST request not worked");
                return false;
            }
        } catch (InterruptedException | IOException e) {
            LOGGER.warning(e.getMessage());
            Thread.currentThread().interrupt();
            return false;
        } finally {
            MockAPI.shutdown();
        }
    }
}
