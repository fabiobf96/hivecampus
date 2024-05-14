package it.hivecampuscompany.hivecampus.boundary;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.hivecampuscompany.hivecampus.exception.MockOpenStreetMapAPIException;
import it.hivecampuscompany.hivecampus.mockapi.MockAPI;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;
import org.apache.http.client.fluent.Request;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * The OpenStreetMapApiBoundary class provides methods for interacting with the OpenStreetMap Nominatim API.
 * It allows the application to retrieve the coordinates of a given address and to obtain a map image of the location.
 */

public class OpenStreetMapApiBoundary {
    private static final Properties properties = LanguageLoader.getLanguageProperties();

    /**
     * Retrieves the latitude and longitude coordinates of a given address using the OpenStreetMap Nominatim API.
     *
     * @param address The address for which to retrieve the coordinates.
     * @return A Point2D object containing the latitude and longitude coordinates.
     * @throws IOException if an error occurs while connecting to the API.
     */

    public static Point2D getCoordinates(String address) throws IOException {
        // Encode the address for URL
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        // OpenStreetMap Nominatim API endpoint
        String urlString = "https://nominatim.openstreetmap.org/search?format=json&q=" + encodedAddress;

        // Create URL object
        URI uri = URI.create(urlString);
        URL url = uri.toURL();

        // Create URLConnection instance
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Parse JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.toString());

            // Check if the response contains any results
            if (!jsonNode.isEmpty()) {
                // Extract latitude and longitude from the first result
                double latitude = jsonNode.get(0).get("lat").asDouble();
                double longitude = jsonNode.get(0).get("lon").asDouble();
                return new Point2D.Double(latitude, longitude);
            } else {
                // No results found
                return new Point2D.Double(0, 0); // Invalid coordinates (return null)
            }
        }
    }

    /**
     * Retrieves a map image of a given address using the OpenStreetMap Nominatim API.
     *
     * @param address The address for which to retrieve the map image.
     * @return A byte array containing the map image.
     * @throws MockOpenStreetMapAPIException if an error occurs while connecting to the API.
     */

    public byte[] getMap(String address) throws MockOpenStreetMapAPIException {
        MockAPI.start();
        MockAPI.mockOpenStreetMapAPI();
        try {
            return  Request.Get("http://localhost:8080/get-map")
                    .execute().returnContent().asBytes();
        } catch (IOException e) {
            throw new MockOpenStreetMapAPIException(properties.getProperty("FAILED_TO_CONNECT_TO_SERVER") + address);
        } finally {
            MockAPI.shutdown();
        }
    }
}
