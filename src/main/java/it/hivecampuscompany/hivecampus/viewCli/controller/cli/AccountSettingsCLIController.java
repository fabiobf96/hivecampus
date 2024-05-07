package it.hivecampuscompany.hivecampus.viewCli.controller.cli;

import it.hivecampuscompany.hivecampus.viewCli.gui.cli.CliGUI;

public class AccountSettingsCLIController extends CLIController {
    public AccountSettingsCLIController(){
        view = new CliGUI();
    }

    @Override
    public void homePage() {

        view.displayWelcomeMessage(properties.getProperty("ACCOUNT_SETTINGS_MSG"));
        view.displayMessage("1. " + properties.getProperty("CHANGE_LANGUAGE_MSG"));
        view.displayMessage("2. " + properties.getProperty("VIEW_PROFILE_MSG"));
        view.displayMessage("3. " + properties.getProperty("GO_BACK_MSG"));

        switch (view.getIntUserInput(properties.getProperty("CHOICE_MSG"))){
            case 1 -> new LanguageCLIController();
            case 2 -> {
                view.displayMessage(properties.getProperty("NOT_IMPLEMENTED_MSG"));
                homePage();
            }
            case 3 -> view.clean();
            default -> invalidChoice();
        }
    }

}
