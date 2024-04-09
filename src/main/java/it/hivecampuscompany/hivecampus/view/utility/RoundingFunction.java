package it.hivecampuscompany.hivecampus.view.utility;

public class RoundingFunction {

    private RoundingFunction(){
        //Default constructor
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
