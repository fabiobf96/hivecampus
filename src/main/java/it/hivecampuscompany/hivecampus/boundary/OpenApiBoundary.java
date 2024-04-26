package it.hivecampuscompany.hivecampus.boundary;

import it.hivecampuscompany.hivecampus.exception.MockOpenAPIException;
import it.hivecampuscompany.hivecampus.mockapi.MockAPI;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class OpenApiBoundary {
    public boolean signContract(byte[] contract) throws MockOpenAPIException {
        //Start mock api for digital sign
        MockAPI.start();
        MockAPI.mockOpenAPI();

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
                throw new MockOpenAPIException("POST request not worked");
            }
        } catch (InterruptedException | IOException e) {
            Thread.currentThread().interrupt();
            throw new MockOpenAPIException(e.getMessage());
        } finally {
            MockAPI.shutdown();
        }
    }
}
