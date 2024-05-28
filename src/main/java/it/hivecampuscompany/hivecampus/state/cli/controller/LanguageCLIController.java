package it.hivecampuscompany.hivecampus.state.cli.controller;

import it.hivecampuscompany.hivecampus.state.cli.ui.CliGUI;
import it.hivecampuscompany.hivecampus.state.utility.LanguageLoader;

import java.util.InputMismatchException;

/**
 * The LanguageCLIController class extends the CLIController and is responsible for handling
 * the language selection interface in the command-line interface (CLI). It allows users to
 * select the desired language and loads the corresponding language properties.
 */

public class LanguageCLIController extends CLIController {

    /**
     * Constructor for the LanguageCLIController class.
     * Initializes the view to a new instance of CliGUI.
     */
    public LanguageCLIController(){
        view = new CliGUI();
    }

    /**
     * Displays the home page where users can select their preferred language.
     * Prompts the user to choose between English and Italian, and loads the
     * corresponding language properties based on the user's choice.
     *
     * @author Fabio Barchiesi
     */
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
