package it.hivecampuscompany.hivecampus.view.controller.cli;

import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;

import java.util.InputMismatchException;

public class LanguageCLIController extends CLIController {
    public LanguageCLIController(){
        view = new CliGUI();
        homePage();
    }
    public void homePage(){

        view.displayWelcomeMessage(properties.getProperty("LANGUAGE_SETTINGS_MSG"));
        view.displayMessage("Select 0 for english");
        view.displayMessage("Seleziona 1 per l'italiano");
        try {
            int choice = view.getIntUserInput(properties.getProperty("CHOICE_MSG"));
            if (choice > -1 && choice < 2){
                LanguageLoader.loadLanguage(choice);
                view.clean();
            }
            else {
                invalidChoice();
            }

        } catch (InputMismatchException e) {
            view.displayMessage(e.getMessage());
            new LanguageCLIController();
        }
    }
}
