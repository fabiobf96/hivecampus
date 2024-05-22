package it.hivecampuscompany.hivecampus.view.gui.cli;

import it.hivecampuscompany.hivecampus.model.Month;
import it.hivecampuscompany.hivecampus.model.Permanence;

import java.util.Arrays;
import java.util.List;

public class FormCliGUI extends CliGUI{

    List<Permanence> permanenceList = Arrays.asList(Permanence.values());

    List<Month> monthList = Arrays.asList(Month.values());

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
        for (Month month : monthList) {
            displayMessage(monthList.indexOf(month) + 1 + ") " +  month.toString());
        }
    }

    public void displayTypesPermanence() {
        for (Permanence permanence : permanenceList) {
            displayMessage(permanenceList.indexOf(permanence) + 1 + ") " + permanence.toString());
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