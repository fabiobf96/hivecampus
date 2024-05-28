package it.hivecampuscompany.hivecampus.state.cli.controller;

/**
 * The OwnerHomeCLIPageController class represents a controller for the home page of the owner in the command-line interface (CLI).
 * It extends the CLIController class and provides methods for displaying owner-specific options and collecting user input.
 */
public class OwnerHomeCLIPageController extends CLIController {
    /**
     * Overrides the homePage method to display the home page for the owner.
     * This method clears the view and displays a welcome message with "HOME_PAGE_MSG" property in uppercase,
     * along with various options available to the owner.
     *
     * @author Fabio Barchiesi
     */
    @Override
    public void homePage() {
        view.clean();
        view.displayWelcomeMessage(properties.getProperty("HOME_PAGE_MSG").toUpperCase());
        view.displayMessage("1. " + properties.getProperty("ACCOUNT_SETTINGS_MSG"));
        view.displayMessage("2. " + properties.getProperty("MANAGE_ADS_MSG"));
        view.displayMessage("3. " + properties.getProperty("MANAGE_REQUEST_MSG"));
        view.displayMessage("4. " + properties.getProperty("MANAGE_CONTRACT_MSG"));
        view.displayMessage("5. " + properties.getProperty("MANAGE_RENT_MSG"));
        view.displayMessage("6. " + properties.getProperty("LOGOUT_MSG"));
    }

    /**
     * Gets the owner's choice from the home page options.
     *
     * @return The integer representing the user's choice.
     * @author Fabio Barchiesi
     */
    public int getChoice() {
        return view.getIntUserInput(properties.getProperty("CHOICE_MSG"));
    }
}