package it.hivecampuscompany.hivecampus.state.cli.controller;

/**
 * The TenantHomeCLIPageController class represents a controller for the home page of the tenant in the command-line interface (CLI).
 * It extends the CLIController class and provides methods for displaying tenant-specific options and collecting user input.
 */

public class TenantHomeCLIPageController extends CLIController {

    public TenantHomeCLIPageController(){
        // Default constructor
    }

    /**
     * Overrides the homePage method to display the home page for the tenant.
     * This method clears the view and displays a welcome message with "HOME_PAGE_MSG" property,
     * along with various options available to the tenant.
     *
     * @author Marina Sotiropoulos
     */

    @Override
    public void homePage() {
        view.clean();
        view.displayWelcomeMessage(properties.getProperty("HOME_PAGE_MSG"));
        view.displayMessage("1. " + properties.getProperty("ACCOUNT_SETTINGS_MSG"));
        view.displayMessage("2. " + properties.getProperty("SEARCH_AD_MSG"));
        view.displayMessage("3. " + properties.getProperty("MANAGE_REQUEST_MSG"));
        view.displayMessage("4. " + properties.getProperty("MANAGE_CONTRACT_MSG"));
        view.displayMessage("5. " + properties.getProperty("MANAGE_RENT_MSG"));
        view.displayMessage("6. " + properties.getProperty("LOGOUT_MSG"));
    }

    /**
     * Gets the tenant's choice from the home page options.
     * @return int value that represents the user's choice.
     * @author Marina Sotiropoulos
     */

    public int getChoice() {
        return view.getIntUserInput(properties.getProperty("CHOICE_MSG"));
    }
}
