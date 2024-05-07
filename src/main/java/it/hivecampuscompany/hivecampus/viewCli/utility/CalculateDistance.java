package it.hivecampuscompany.hivecampus.viewCli.utility;

public class CalculateDistance {

    // Raggio della Terra in chilometri
    private static final double EARTH_RADIUS_KM = 6371.0;

    private CalculateDistance() {
        // Default constructor
    }

    // Calcola la distanza tra due coordinate geografiche in chilometri utilizzando la formula di Haversine
    public static double haversineFormula (double lon1, double lat1, double lon2, double lat2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distanceInMeters = EARTH_RADIUS_KM * c * 1000; // Converti la distanza in metri
        return RoundingFunction.roundingDouble(distanceInMeters / 1000); // Converti la distanza in chilometri arrotondata
    }
}