package it.hivecampuscompany.hivecampus.state.cli.controller;

/**
 * The InitialCLIPageController class represents a controller for the initial command-line interface (CLI) page.
 * It extends the CLIController class and provides methods for displaying the home page and getting user input.
 */
public class InitialCLIPageController extends CLIController {

    /**
     * Displays the home page of the CLI interface.
     * This method clears the view, then displays welcome message and menu options.
     */
    public void homePage() {
        view.clean();
        view.displayWelcomeMessage(properties.getProperty("WELCOME_MSG"));
        view.displayMessage("1. " + properties.getProperty("CHANGE_LANGUAGE_MSG"));
        view.displayMessage("2. " + properties.getProperty("LOGIN_MSG"));
        view.displayMessage("3. " + properties.getProperty("SIGN_UP_MSG"));
        view.displayMessage("4. " + properties.getProperty("EXIT_MSG"));
    }

    /**
     * Gets the user's choice from the CLI interface.
     *
     * @return The integer representing the user's choice.
     */
    public int getChoice() {
        return view.getIntUserInput(properties.getProperty("CHOICE_MSG"));
    }
}
