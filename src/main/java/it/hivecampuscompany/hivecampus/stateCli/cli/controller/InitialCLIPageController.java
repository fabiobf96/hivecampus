package it.hivecampuscompany.hivecampus.stateCli.cli.controller;

import it.hivecampuscompany.hivecampus.viewCli.controller.cli.CLIController;
import it.hivecampuscompany.hivecampus.viewCli.gui.cli.CliGUI;
import it.hivecampuscompany.hivecampus.viewCli.utility.LanguageLoader;

public class InitialCLIPageController extends CLIController {
    public InitialCLIPageController() {
        properties = LanguageLoader.getLanguageProperties();
        view = new CliGUI();
    }

    public void homePage() {
        view.clean();
        view.displayWelcomeMessage(properties.getProperty("WELCOME_MSG"));
        view.displayMessage("1. " + properties.getProperty("CHANGE_LANGUAGE_MSG"));
        view.displayMessage("2. " + properties.getProperty("LOGIN_MSG"));
        view.displayMessage("3. " + properties.getProperty("SIGN_UP_MSG"));
        view.displayMessage("4. " + properties.getProperty("EXIT_MSG"));
    }

    public int getChoice() {
        return view.getIntUserInput(properties.getProperty("CHOICE_MSG"));
    }
}
