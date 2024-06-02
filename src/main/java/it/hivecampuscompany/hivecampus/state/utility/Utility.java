package it.hivecampuscompany.hivecampus.state.utility;

/**
 * Utility class provides some useful methods to perform operations on data.
 * Particularly, it provides a method to calculate the distance between two geographic coordinates,
 * a method to format a text in a more readable way and a method to round a double number.
 *
 * @author Marina Sotiropoulos
 */

public class Utility {

    private static final double EARTH_RADIUS_KM = 6371.0;  // Raggio della Terra in chilometri

    // Prevent instantiation of the utility class with a private constructor
    private Utility() {
    }

    /**
     * Calculate the distance between two geographic coordinates in kilometers using the Haversine formula.
     * The formula calculates the shortest distance between two points on the surface of a sphere.
     *
     * @param lon1 Longitude of the first point
     * @param lat1 Latitude of the first point
     * @param lon2 Longitude of the second point
     * @param lat2 Latitude of the second point
     * @return The distance between the two points in kilometers
     *
     * @author Marina Sotiropoulos
     */


    public static double calculateDistance (double lon1, double lat1, double lon2, double lat2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.pow(Math.sin(dLon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = EARTH_RADIUS_KM * c;
        return roundingDouble(distance);
    }

    /**
     * Format a text in a more readable way by splitting it into lines of 60 characters.
     * It adds a new line character when the line reaches the maximum length.
     *
     * @param text The text to format
     * @return The formatted text
     *
     * @author Marina Sotiropoulos
     */

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
        formattedText.append(currentLine);
        return formattedText.toString();
    }

    /**
     * Round a double number to the nearest integer.
     * It uses the threshold value of 0.5 to decide whether to round up or down.
     *
     * @param value The double number to round
     * @return The rounded number
     *
     * @author Marina Sotiropoulos
     */

    public static double roundingDouble (double value) {

        double d = 0.5F;

        double decimalPart = value - Math.floor(value);

        double result;
        if (decimalPart >= d) {
            result = Math.ceil(value);
        } else {
            result = Math.floor(value);
        }
        return result;
    }
}