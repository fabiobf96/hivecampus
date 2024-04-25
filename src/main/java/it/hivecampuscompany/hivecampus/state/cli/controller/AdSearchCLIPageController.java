package it.hivecampuscompany.hivecampus.state.cli.controller;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.manager.AdSearchManager;
import it.hivecampuscompany.hivecampus.view.controller.cli.CLIController;
import java.util.List;

public class AdSearchCLIPageController extends CLIController {

    private final AdSearchManager adSearchManager;

    public AdSearchCLIPageController(){
        adSearchManager = new AdSearchManager();
    }
    @Override
    public void homePage() {
        view.clean();
        view.displayWelcomeMessage(properties.getProperty("SEARCH_AD_MSG").toUpperCase());
    }

    public List<AdBean> searchAds() {
        String university = getField(properties.getProperty("UNIVERSITY_FIELD_REQUEST_MSG"), false);
        float maxDistance = getValidatedInput(properties.getProperty("MAX_DISTANCE_FIELD_REQUEST_MSG"), 15F);
        int maxPrice = getValidatedInput(properties.getProperty("MAX_PRICE_FIELD_REQUEST_MSG"), 1000F).intValue();
        boolean privateBath = getBooleanInput(properties.getProperty("PRIVATE_BATH_FIELD_REQUEST_MSG"));
        boolean balcony = getBooleanInput(properties.getProperty("BALCONY_FIELD_REQUEST_MSG"));
        boolean conditioner = getBooleanInput(properties.getProperty("CONDITIONER_FIELD_REQUEST_MSG"));
        boolean tvConnection = getBooleanInput(properties.getProperty("TV_CONNECTION_FIELD_REQUEST_MSG"));

        FiltersBean filtersBean = new FiltersBean(university, maxDistance, maxPrice, privateBath, balcony, conditioner, tvConnection);

        // Retrieve the ads that match the filters
        return adSearchManager.searchAdsByFilters(filtersBean);
    }

    public AdBean showAdsPreview(List<AdBean> adBeans) {
        return selectFromList(adBeans, "preview");
    }

    public void showAdDetails(AdBean adBean) {
        view.clean();
        view.displayWelcomeMessage("Ad Details");
        view.displayMessage(properties.getProperty("ROOM_TYPE_MSG") + adBean.getDetails());
        view.displayMessage("1. " + properties.getProperty("LEASE_REQUEST_FOR_AD_MSG"));
        view.displayMessage("2. " + properties.getProperty("GO_BACK_MSG"));
    }

    public int getChoice() {
        return view.getIntUserInput(properties.getProperty("CHOICE_MSG"));
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
        // Verifica se l'ingresso è vuoto o non rappresenta un numero
        if (input.isEmpty()) {
            return defaultValue;
        }
        try {
            float floatValue = Float.parseFloat(input);
            // Verifica se il valore supera il limite
            if (floatValue > defaultValue) {
                view.displayMessage(properties.getProperty("EXCEED_LIMIT_VALUE_MSG"));
                return null; // Restituisci null per indicare un input non valido
            }
            return floatValue;
        } catch (NumberFormatException e) {
            view.displayMessage(properties.getProperty("EXCEED_LIMIT_VALUE_MSG"));
            return null; // Restituisci null per indicare un input non valido
        }
    }
}
