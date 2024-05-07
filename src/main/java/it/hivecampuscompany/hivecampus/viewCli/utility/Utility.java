package it.hivecampuscompany.hivecampus.view.utility;

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

public class Utility {

    private static final Logger LOGGER = Logger.getLogger(Utility.class.getName());
    private static final double EARTH_RADIUS_KM = 6371.0;  // Raggio della Terra in chilometri

    private Utility() {
        // Default constructor
    }

    // Calcola la distanza tra due coordinate geografiche in chilometri utilizzando la formula di Haversine
    public static double calculateDistance (double lon1, double lat1, double lon2, double lat2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distanceInMeters = EARTH_RADIUS_KM * c * 1000; // Converti la distanza in metri
        return Utility.roundingDouble(distanceInMeters / 1000); // Converti la distanza in chilometri arrotondata
    }

    public static String formatText(String text) {
        StringBuilder formattedText = new StringBuilder();
        StringBuilder currentLine = new StringBuilder();
        int charCount = 0;

        for (char c : text.toCharArray()) {
            currentLine.append(c);
            charCount++;

            if (charCount >= 60 && (c == ' ' || c == '.' || c == ':')) {
                formattedText.append(currentLine).append("\n");
                currentLine.setLength(0);
                charCount = 0;
            }
        }
        formattedText.append(currentLine); // Aggiungi l'ultima riga rimanente
        return formattedText.toString();
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

    public static double roundingDouble (double value) {
        // Specifica il valore di soglia per decidere l'arrotondamento
        double d = 0.5F;

        // Estrai la parte decimale
        double decimalPart = value - Math.floor(value);

        // Decide se arrotondare per eccesso o per difetto
        double result;
        if (decimalPart >= d) {
            result = Math.ceil(value);  // Arrotonda per eccesso
        } else {
            result = Math.floor(value);  // Arrotonda per difetto
        }
        return result;
    }
}
