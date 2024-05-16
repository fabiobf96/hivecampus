package it.hivecampuscompany.hivecampus.view.controller.cli;

import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;

/**
 *  Controller class for the Account Settings CLI page.
 *  It extends the CLIController class and provides methods to display the home page.
 */

public class AccountSettingsCLIController extends CLIController {
    public AccountSettingsCLIController() {
        view = new CliGUI();
    }

    /**
     * Method to display the home page.
     * It displays a welcome message and the options to change the language,
     * view the profile, or go back.
     */

    @Override
    public void homePage() {

        view.displayWelcomeMessage(properties.getProperty("ACCOUNT_SETTINGS_MSG"));
        view.displayMessage("1. " + properties.getProperty("CHANGE_LANGUAGE_MSG"));
        view.displayMessage("2. " + properties.getProperty("VIEW_PROFILE_MSG"));
        view.displayMessage("3. " + properties.getProperty("GO_BACK_MSG"));

        switch (view.getIntUserInput(properties.getProperty("CHOICE_MSG"))) {
            case 1 -> {
                LanguageCLIController languageCLIController = new LanguageCLIController();
                languageCLIController.homePage();
            }
            case 2 -> {
                view.displayMessage(properties.getProperty("NOT_IMPLEMENTED_MSG"));
                homePage();
            }
            case 3 -> view.clean();
            default -> invalidChoice();
        }
    }

}
