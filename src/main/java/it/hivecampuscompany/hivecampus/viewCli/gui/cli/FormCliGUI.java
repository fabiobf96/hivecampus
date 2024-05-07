package it.hivecampuscompany.hivecampus.viewCli.gui.cli;

import java.util.Arrays;
import java.util.List;

public class FormCliGUI extends CliGUI{

    List<String> months = Arrays.asList(
            properties.getProperty("JANUARY_MSG"),
            properties.getProperty("FEBRUARY_MSG"),
            properties.getProperty("MARCH_MSG"),
            properties.getProperty("APRIL_MSG"),
            properties.getProperty("MAY_MSG"),
            properties.getProperty("JUNE_MSG"),
            properties.getProperty("JULY_MSG"),
            properties.getProperty("AUGUST_MSG"),
            properties.getProperty("SEPTEMBER_MSG"),
            properties.getProperty("OCTOBER_MSG"),
            properties.getProperty("NOVEMBER_MSG"),
            properties.getProperty("DECEMBER_MSG")
    );

    List<String> typesPermanence = Arrays.asList(
            properties.getProperty("SIX_MONTHS_MSG"),
            properties.getProperty("TWELVE_MONTHS_MSG"),
            properties.getProperty("TWENTY_FOUR_MONTHS_MSG"),
            properties.getProperty("THIRTY_SIX_MONTHS_MSG")
    );

    List<String> typesHome = Arrays.asList(
            properties.getProperty("STUDIO_APARTMENT_MSG"), // Monolocale
            properties.getProperty("ONE_BEDROOM_APARTMENT_MSG"), // Bilocale
            properties.getProperty("TWO_BEDROOM_APARTMENT_MSG"), // Trilocale
            properties.getProperty("THREE_BEDROOM_APARTMENT_MSG") // Quadrilocale
    );

    List<String> typesRoom = Arrays.asList(
            properties.getProperty("SINGLE_ROOM_MSG"), // Singola
            properties.getProperty("DOUBLE_ROOM_MSG")   // Doppia
    );

    public void displayMonths() {
        for (String month : months) {
            displayMessage(months.indexOf(month) + 1 + ". " + month);
        }
    }

    public void displayTypesPermanence() {
        for (String type : typesPermanence) {
            displayMessage(typesPermanence.indexOf(type) + 1 + ") " + type);
        }
    }

    public void displayTypesHome() {
        for (String type : typesHome) {
            displayMessage(typesHome.indexOf(type) + 1 + ") " + type);
        }
    }

    public void displayTypesRoom() {
        for (String type : typesRoom) {
            displayMessage(typesRoom.indexOf(type) + 1 + ") " + type);
        }
    }
}