package it.hivecampuscompany.hivecampus.cli.controller;

import it.hivecampuscompany.hivecampus.cli.view.CLIView;
import it.hivecampuscompany.hivecampus.utility.LanguageLoader;

import java.util.InputMismatchException;

public class LanguageCLIController extends CLIController{
    public LanguageCLIController(){
        view = new CLIView();
        homePage();
    }
    public void homePage(){
        view.displayMessage("Select 0 for english");
        view.displayMessage("Seleziona 1 per l'italiano");
        try {
            int choice = view.getIntUserInput(properties.getProperty("CHOICE_MSG"));
            if (choice > 1 && choice < -1){
                invalidChoice();
                homePage();
            }
            LanguageLoader.loadLanguage(choice);
        } catch (InputMismatchException e) {
            view.displayMessage(e.getMessage());
            new LanguageCLIController();
        }
    }
}
