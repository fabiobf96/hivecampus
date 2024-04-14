package it.hivecampuscompany.hivecampus.state.cli.controller;

import it.hivecampuscompany.hivecampus.view.controller.cli.CLIController;
import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;

import java.util.InputMismatchException;

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
        try {
            return view.getIntUserInput(properties.getProperty("CHOICE_MSG"));
        } catch (InputMismatchException e) {
            return 0;
        }
    }
}
