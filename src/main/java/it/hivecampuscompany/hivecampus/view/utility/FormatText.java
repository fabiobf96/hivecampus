package it.hivecampuscompany.hivecampus.view.utility;

public class FormatText {

    private FormatText(){
        // Default constructor
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
}
