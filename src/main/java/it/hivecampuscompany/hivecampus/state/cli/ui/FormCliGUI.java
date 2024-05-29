package it.hivecampuscompany.hivecampus.state.cli.ui;

import it.hivecampuscompany.hivecampus.model.Month;
import it.hivecampuscompany.hivecampus.model.Permanence;

import java.util.Arrays;
import java.util.List;

/**
 * The FormCliGUI class extends CliGUI and provides functionality for displaying
 * lists of options related to months, types of permanence, types of homes, and types of rooms.
 *
 * @author Marina Sotiropoulos
 */
public class FormCliGUI extends CliGUI {

    // List of permanence options
    List<Permanence> permanenceList = Arrays.asList(Permanence.values());

    // List of month options
    List<Month> monthList = Arrays.asList(Month.values());

    // List of home types loaded from properties
    List<String> typesHome = Arrays.asList(
            properties.getProperty("STUDIO_APARTMENT_MSG"), // Studio Apartment
            properties.getProperty("TWO_BEDROOM_APARTMENT_MSG"), // One-Bedroom Apartment
            properties.getProperty("THREE_BEDROOM_APARTMENT_MSG"), // Two-Bedroom Apartment
            properties.getProperty("FOUR_BEDROOM_APARTMENT_MSG") // Three-Bedroom Apartment
    );

    // List of room types loaded from properties
    List<String> typesRoom = Arrays.asList(
            properties.getProperty("SINGLE_ROOM_MSG"), // Single Room
            properties.getProperty("DOUBLE_ROOM_MSG") // Double Room
    );

    /**
     * Displays the list of months.
     * Each month is displayed with its corresponding index.
     *
     * @author Marina Sotiropoulos
     */
    public void displayMonths() {
        for (Month month : monthList) {
            displayMessage(monthList.indexOf(month) + 1 + ") " + month.toString());
        }
    }

    /**
     * Displays the list of types of permanence.
     * Each type of permanence is displayed with its corresponding index.
     *
     * @author Marina Sotiropoulos
     */
    public void displayTypesPermanence() {
        for (Permanence permanence : permanenceList) {
            displayMessage(permanenceList.indexOf(permanence) + 1 + ") " + permanence.toString());
        }
    }

    /**
     * Displays the list of home types.
     * Each home type is displayed with its corresponding index.
     *
     * @author Marina Sotiropoulos
     */
    public void displayTypesHome() {
        for (String type : typesHome) {
            displayMessage(typesHome.indexOf(type) + 1 + ") " + type);
        }
    }

    /**
     * Displays the list of room types.
     * Each room type is displayed with its corresponding index.
     *
     * @author Marina Sotiropoulos
     */
    public void displayTypesRoom() {
        for (String type : typesRoom) {
            displayMessage(typesRoom.indexOf(type) + 1 + ") " + type);
        }
    }
}