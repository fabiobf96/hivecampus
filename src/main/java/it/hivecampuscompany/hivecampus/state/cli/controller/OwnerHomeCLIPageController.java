package it.hivecampuscompany.hivecampus.state.cli.controller;

import it.hivecampuscompany.hivecampus.view.controller.cli.CLIController;
import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;

import java.util.InputMismatchException;

public class OwnerHomeCLIPageController extends CLIController{
        public OwnerHomeCLIPageController() {
            properties = LanguageLoader.getLanguageProperties();
            view = new CliGUI();
        }
        @Override
        public void homePage() {
            view.clean();
            view.displayWelcomeMessage(properties.getProperty("HOME_PAGE_MSG").toUpperCase());
            view.displayMessage("1. " + properties.getProperty("ACCOUNT_SETTINGS_MSG"));
            view.displayMessage("2. " + properties.getProperty("MANAGE_ADS_MSG"));
            view.displayMessage("3. " + properties.getProperty("MANAGE_REQUEST_MSG"));
            view.displayMessage("4. " + properties.getProperty("MANAGE_CONTRACT_MSG"));
            view.displayMessage("5. " + properties.getProperty("MANAGE_RENT_MSG"));
            view.displayMessage("6. " + properties.getProperty("EXIT_MSG"));
        }

        public int getChoice() {
            try {
                return view.getIntUserInput(properties.getProperty("CHOICE_MSG"));
            } catch (InputMismatchException e) {
                return 0;
            }
        }
    }

