package it.hivecampuscompany.hivecampus.viewCli.utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class Geocoding {

    private static final Logger LOGGER = Logger.getLogger(Geocoding.class.getName());

    private Geocoding() {
        // Private constructor to hide the implicit public one
    }
    public static Point2D getCoordinates(String address) {
        // Encode the address for URL
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        // OpenStreetMap Nominatim API endpoint
        String urlString = "https://nominatim.openstreetmap.org/search?format=json&q=" + encodedAddress;
        try {
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
        } catch (IOException e) {
            LOGGER.severe("Failed to retrieve coordinates from OpenStreetMap Nominatim API");
            return null;
        }
    }
}
