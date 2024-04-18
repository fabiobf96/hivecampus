package it.hivecampuscompany.hivecampus.view.controller.cli;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.manager.AdSearchManager;
import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;
import java.util.List;

public class AdSearchCLIController extends CLIController {
    private final AdSearchManager adSearchManager;

public AdSearchCLIController() {
        view = new CliGUI();
        adSearchManager = new AdSearchManager();
        homePage();
    }

    @Override
    public void homePage() {
        view.displayWelcomeMessage(properties.getProperty("SEARCH_ROOM_MSG").toUpperCase());
        searchAds();
    }

    public void searchAds() {
        String university = getField(properties.getProperty("UNIVERSITY_FIELD_REQUEST_MSG"), false);
        float maxDistance = getValidatedInput(properties.getProperty("MAX_DISTANCE_FIELD_REQUEST_MSG"), 15F);
        int maxPrice = getValidatedInput(properties.getProperty("MAX_PRICE_FIELD_REQUEST_MSG"), 1000F).intValue();
        boolean privateBath = getBooleanInput(properties.getProperty("PRIVATE_BATH_FIELD_REQUEST_MSG"));
        boolean balcony = getBooleanInput(properties.getProperty("BALCONY_FIELD_REQUEST_MSG"));
        boolean conditioner = getBooleanInput(properties.getProperty("CONDITIONER_FIELD_REQUEST_MSG"));
        boolean tvConnection = getBooleanInput(properties.getProperty("TV_CONNECTION_FIELD_REQUEST_MSG"));

        FiltersBean filtersBean = new FiltersBean(university, maxDistance, maxPrice, privateBath, balcony, conditioner, tvConnection);

        // Retrieve the ads that match the filters
        List<AdBean> adBeans = adSearchManager.searchAdsByFilters(filtersBean);

        // Display the preview of the ads
        showAdsPreview(adBeans);

        // Call the controller to display the details of the selected ad
        new AdDetailsCLIController(adBeans);
    }

    private void showAdsPreview(List<AdBean> adBeans) {
        for (int i = 1; i <= adBeans.size(); i++) {
            view.displayMessage(i + ") " + adBeans.get(i - 1).getPreview());
        }
    }

    private Float getValidatedInput(String message, float defaultValue) {
        Float input;
        do {
            String userInput = view.getStringUserInput(message);
            input = validateNumericInput(userInput, defaultValue);
        } while (input == null); // Keep looping until valid input is provided
        return input;
    }

    private Float validateNumericInput(String input, float defaultValue) {
        // Verifica se l'input è vuoto o non rappresenta un numero
        if (input.isEmpty()) {
            return defaultValue;
        }
        try {
            float floatValue = Float.parseFloat(input);
            // Verifica se il valore supera il limite
            if (floatValue > defaultValue) {
                view.displayMessage("Il valore inserito supera il limite.");
                return null; // Restituisci null per indicare un input non valido
            }
            return floatValue;
        } catch (NumberFormatException e) {
            view.displayMessage("Il valore inserito non è un numero valido.");
            return null; // Restituisci null per indicare un input non valido
        }
    }
}
