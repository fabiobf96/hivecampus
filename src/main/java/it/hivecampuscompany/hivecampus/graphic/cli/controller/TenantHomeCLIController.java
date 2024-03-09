package it.hivecampuscompany.hivecampus.graphic.cli.controller;

import it.hivecampuscompany.hivecampus.graphic.cli.view.CLIView;
import it.hivecampuscompany.hivecampus.graphic.utility.LanguageLoader;
import it.hivecampuscompany.hivecampus.logic.bean.SessionBean;

public class TenantHomeCLIController extends CLIController{

    public TenantHomeCLIController(){
        properties = LanguageLoader.getLanguageProperties();
        view = new CLIView();
        homePage();

    }
    public TenantHomeCLIController(SessionBean sessionBean){
        this.sessionBean = sessionBean;
        view = new CLIView();
        homePage();

    }

    @Override
    public void homePage() {

        view.displayWelcomeMessage(properties.getProperty("HOME_PAGE_MSG"));
        view.displayMessage("1. " + properties.getProperty("ACCOUNT_SETTINGS_MSG"));
        view.displayMessage("2. " + properties.getProperty("SEARCH_ROOM_MSG"));
        view.displayMessage("3. " + properties.getProperty("MANAGE_REQUEST_MSG"));
        view.displayMessage("4. " + properties.getProperty("MANAGE_CONTRACT_MSG"));
        view.displayMessage("5. " + properties.getProperty("MANAGE_RENT_MSG"));
        view.displayMessage("6. " + properties.getProperty("EXIT_MSG"));

        switch (view.getIntUserInput(properties.getProperty("CHOICE_MSG"))){
            case 1 -> {
                new AccountSettingsCLIController();
                this.properties = LanguageLoader.getLanguageProperties();
                homePage();
            }
            case 2 -> {
                //RoomSearchCLIController roomSearchCLIController = new RoomSearchCLIController(sessionBean);
                view.displayMessage(properties.getProperty("SEARCH ROOM"));
                homePage();
            }
            case 3 -> {
                System.out.println("MANAGE REQUESTS");
                homePage();
            }
            case 4 -> {
                System.out.println("MANAGE CONTRACT");
                homePage();
            }
            case 5 -> {
                System.out.println("MANAGE RENT RATES");
                homePage();
            }
            case 6 -> {
                exit();
            }
            default -> invalidChoice();
        }


    }

    public static void main (String[] args) {
        new TenantHomeCLIController();
    }

}
