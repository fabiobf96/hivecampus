package it.hivecampuscompany.hivecampus.view.controller.cli;

import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;

public class TenantHomeCLIController extends CLIController{
    public TenantHomeCLIController(){
        properties = LanguageLoader.getLanguageProperties();
        view = new CliGUI();
        homePage();

    }
    public TenantHomeCLIController(SessionBean sessionBean){
        this.sessionBean = sessionBean;
        view = new CliGUI();
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
                view.clean();
                new AccountSettingsCLIController();
                this.properties = LanguageLoader.getLanguageProperties();
                homePage();
            }
            case 2 -> {
                view.clean();
                new RoomSearchCLIController();
                homePage();
            }
            case 3 -> {
                view.displayMessage("MANAGE REQUESTS");
                homePage();
            }
            case 4 -> {
                view.displayMessage("MANAGE CONTRACT");
                homePage();
            }
            case 5 -> {
                view.displayMessage("MANAGE RENT RATES");
                homePage();
            }
            case 6 -> exit();

            default -> invalidChoice();
        }


    }

    public static void main (String[] args) {
        new TenantHomeCLIController();
    }

}
