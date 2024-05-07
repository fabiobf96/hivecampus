package it.hivecampuscompany.hivecampus.stateCli.cli.controller;

import it.hivecampuscompany.hivecampus.viewCli.controller.cli.CLIController;
public class TenantHomeCLIPageController extends CLIController {

    public TenantHomeCLIPageController(){
        // Default constructor
    }
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

    public int getChoice() {
        return view.getIntUserInput(properties.getProperty("CHOICE_MSG"));
    }
}
