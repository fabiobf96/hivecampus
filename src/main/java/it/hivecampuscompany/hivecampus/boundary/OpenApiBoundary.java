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

/**
 * This class represents a boundary for interacting with an OpenAPI for signing contracts.
 *
 * @author Fabio Barchiesi
 */
public class OpenApiBoundary {

    /**
     * Signs a contract using a mock API.
     *
     * @param contract The byte array representing the contract to be signed.
     * @return true if the contract was successfully signed, false otherwise.
     * @throws MockOpenAPIException if there is an error during the signing process.
     * @author Fabio Barchiesi
     */
    public boolean signContract(byte[] contract) throws MockOpenAPIException {
        // Start mock API for digital sign
        MockAPI.start();
        MockAPI.mockOpenAPI();

        try (HttpClient client = HttpClient.newHttpClient()) {
            // Create the HTTP request to sign the document
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/sign-document"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofByteArray(contract))
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Get the response code
            int responseCode = response.statusCode();

            // Check if the response indicates success
            if (responseCode == HttpURLConnection.HTTP_OK && response.body().equals("true")) {
                return true;
            } else {
                throw new MockOpenAPIException("POST request not worked");
            }
        } catch (InterruptedException | IOException e) {
            // Restore interrupted state...
            Thread.currentThread().interrupt();
            throw new MockOpenAPIException(e.getMessage());
        } finally {
            // Shut down the mock API
            MockAPI.shutdown();
        }
    }
}

