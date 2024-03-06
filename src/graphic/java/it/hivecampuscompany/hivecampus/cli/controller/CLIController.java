package it.hivecampuscompany.hivecampus.cli.controller;

import it.hivecampuscompany.hivecampus.utility.LanguageLoader;
import it.hivecampuscompany.hivecampus.cli.view.CLIView;

import java.util.Properties;

public abstract class CLIController {
    protected CLIView view;
    protected Properties properties;

    protected CLIController(){
        properties = LanguageLoader.getLanguageProperties();
    }
    protected void invalidChoice() {
        view.displayMessage(properties.getProperty("INVALID_OPTION_MSG"));
    }
    protected String getField(String nameField){
        String field;
        boolean first = true;
        do {
            if(!first){
                view.displayMessage(properties.getProperty("EMPTY_FIELD_MSG"));
            }
            first = false;
            field = view.getStringUserInput(nameField);
        } while (field.isEmpty());
        return field;
    }

    protected void exit(){
        view.displayMessage(properties.getProperty("GOODBYE_MSG"));
        System.exit(0);
    }

    public abstract void homePage();
}
