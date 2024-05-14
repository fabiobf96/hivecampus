package it.hivecampuscompany.hivecampus.state.cli.controller;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.FiltersBean;
import it.hivecampuscompany.hivecampus.manager.AdManager;
import it.hivecampuscompany.hivecampus.view.controller.cli.CLIController;
import java.util.Collections;
import java.util.List;

/**
 * Controller class for the Ad Search CLI page. It extends the CLIController class and uses the AdManager class to search ads by filters.
 * It provides methods to display the search form, search ads, show ad preview, show ad details, and get user's choice.
 */


public class AdSearchCLIPageController extends CLIController {

    private final AdManager manager;

    /**
     * Constructor for the AdSearchCLIPageController class.
     */

    public AdSearchCLIPageController(){
        manager = new AdManager();
    }

    /**
     * Method to display the home page. It displays a welcome message.
     */

    @Override
    public void homePage() {
        view.clean();
        view.displayWelcomeMessage(properties.getProperty("SEARCH_AD_MSG").toUpperCase());
    }

    /**
     * Method to display the search form.
     * @return List of ads that match the filters.
     */

    public List<AdBean> searchAds() {
        String university = getField(properties.getProperty("UNIVERSITY_FIELD_REQUEST_MSG"), false);
        float maxDistance = getValidatedInput(properties.getProperty("MAX_DISTANCE_FIELD_REQUEST_MSG"), 15F);
        int maxPrice = getValidatedInput(properties.getProperty("MAX_PRICE_FIELD_REQUEST_MSG"), 1000F).intValue();
        boolean privateBath = getBooleanInput(properties.getProperty("PRIVATE_BATH_FIELD_REQUEST_MSG"));
        boolean balcony = getBooleanInput(properties.getProperty("BALCONY_FIELD_REQUEST_MSG"));
        boolean conditioner = getBooleanInput(properties.getProperty("CONDITIONER_FIELD_REQUEST_MSG"));
        boolean tvConnection = getBooleanInput(properties.getProperty("TV_CONNECTION_FIELD_REQUEST_MSG"));

        FiltersBean filtersBean = new FiltersBean(university, maxDistance, maxPrice, privateBath, balcony, conditioner, tvConnection);

        List<AdBean> adBeans = manager.searchAdsByFilters(filtersBean);
        if (adBeans.isEmpty()) {
            view.displayMessage("\n" + properties.getProperty("ERROR_SEARCH_MSG"));
            pause();
            return Collections.emptyList();
        }
        return adBeans;
    }

    /**
     * Method to display the ad preview.
     * @param adBeans List of ads to display.
     * @return AdBean object.
     */

    public AdBean showAdsPreview(List<AdBean> adBeans) {
        return selectFromList(adBeans, "preview"); // cambiare modo di stampa come in showAdDetails
    }

    /**
     * Method to call the showAdDetails method and display the options to send lease request or go back.
     * @param adBean AdBean object to display.
     */

    public void showAdDetails(AdBean adBean) {
        view.clean();
        view.displayWelcomeMessage(properties.getProperty("AD_DETAILS"));
        displayAdDetails(adBean);
        view.displayMessage("\n1. " + properties.getProperty("LEASE_REQUEST_FOR_AD_MSG"));
        view.displayMessage("2. " + properties.getProperty("GO_BACK_MSG"));
    }

    /**
     * Method to display the ad details.
     * @param adBean AdBean object to display.
     */

    private void displayAdDetails(AdBean adBean) {
        view.displayMessage(properties.getProperty("ROOM_TYPE_MSG") + adBean.adTitle());
        view.displayMessage(properties.getProperty("HOME_FEATURES_MSG") + adBean.getHomeBean().getDetails());
        view.displayMessage(properties.getProperty("ROOM_FEATURES_MSG") + adBean.getRoomBean().getDetails());
        view.displayMessage(properties.getProperty("MONTH_AVAILABILITY_MSG") + adBean.getAdStart().toString());
        view.displayMessage(properties.getProperty("OWNER_INFO_MSG") + adBean.getOwnerBean().getDetails());
    }

    /**
     * Method to get the user's choice.
     * @return int value that represents the user's choice.
     */

    public int getChoice() {
        return view.getIntUserInput(properties.getProperty("CHOICE_MSG"));
    }

    /**
     * Method to get a valid input from the user.
     * It keeps looping until a valid input is provided.
     *
     * @param message Message to display.
     * @param defaultValue Default value to compare with.
     * @return Float value.
     */

    private Float getValidatedInput(String message, float defaultValue) {
        Float input;
        do {
            String userInput = view.getStringUserInput(message);
            input = validateNumericInput(userInput, defaultValue);
        } while (input == null);
        return input;
    }

    /**
     * Method to check whether the input is empty or does not represent a number
     * or if the value exceeds the limit.
     *
     * @param input User input.
     * @param defaultValue Default value to compare with.
     * @return null if the input is invalid, otherwise the float value or defaultValue.
     */

    private Float validateNumericInput(String input, float defaultValue) {
        if (input.isEmpty()) {
            return defaultValue;
        }
        try {
            float floatValue = Float.parseFloat(input);

            if (floatValue > defaultValue) {
                view.displayMessage(properties.getProperty("EXCEED_LIMIT_VALUE_MSG"));
                return null;
            }
            return floatValue;
        } catch (NumberFormatException e) {
            view.displayMessage(properties.getProperty("EXCEED_LIMIT_VALUE_MSG"));
            return null;
        }
    }
}
