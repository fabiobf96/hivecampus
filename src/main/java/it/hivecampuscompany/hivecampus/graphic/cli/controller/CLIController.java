package it.hivecampuscompany.hivecampus.graphic.cli.controller;

import it.hivecampuscompany.hivecampus.graphic.utility.LanguageLoader;
import it.hivecampuscompany.hivecampus.graphic.cli.view.CLIView;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;
import it.hivecampuscompany.hivecampus.logic.manager.SessionManager;

import java.util.Properties;

public abstract class CLIController {
    protected CLIView view;
    protected Properties properties;
    protected SessionBean sessionBean;

    protected CLIController(){
        properties = LanguageLoader.getLanguageProperties();
    }
    protected void invalidChoice() {
        view.displayMessage(properties.getProperty("INVALID_OPTION_MSG"));
        // ricordati di mettorlo dentro al file properties
        view.getStringUserInput("press any key to continue");
        view.clean();
        homePage();
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
        if (sessionBean != null){
            SessionManager sessionManager = SessionManager.getInstance();
            sessionManager.deleteSession(sessionBean);
        }
        view.displayMessage(properties.getProperty("GOODBYE_MSG"));
        System.exit(0);
    }

    public abstract void homePage();
}
