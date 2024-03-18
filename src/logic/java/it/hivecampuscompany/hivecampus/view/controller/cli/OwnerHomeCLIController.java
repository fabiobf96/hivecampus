package it.hivecampuscompany.hivecampus.view.controller.cli;

import it.hivecampuscompany.hivecampus.view.gui.cli.CLIView;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;
import it.hivecampuscompany.hivecampus.bean.SessionBean;

import java.util.InputMismatchException;

public class OwnerHomeCLIController extends CLIController {
    public OwnerHomeCLIController(SessionBean sessionBean) {
        this();
        this.sessionBean = sessionBean;
        homePage();
    }
    public OwnerHomeCLIController(){
        view = new CLIView();
    }
    @Override
    public void homePage() {
        view.displayWelcomeMessage(properties.getProperty("HOME_PAGE_MSG").toUpperCase());
        view.displayMessage("1. " + properties.getProperty("ACCOUNT_SETTINGS_MSG"));
        view.displayMessage("2. " + properties.getProperty("MANAGE_ADS_MSG"));
        view.displayMessage("3. " + properties.getProperty("MANAGE_REQUEST_MSG"));
        view.displayMessage("4. " + properties.getProperty("MANAGE_CONTRACT_MSG"));
        view.displayMessage("5. " + properties.getProperty("MANAGE_RENT_MSG"));
        view.displayMessage("6. " + properties.getProperty("EXIT_MSG"));
        try {

            switch (view.getIntUserInput(properties.getProperty("CHOICE_MSG"))) {
                case 1 -> {
                    view.clean();
                    new AccountSettingsCLIController();
                    properties = LanguageLoader.getLanguageProperties();
                    homePage();
                }
                case 6 -> exit();
                default -> invalidChoice();
            }
        } catch (InputMismatchException e){
            invalidChoice();
            homePage();
        }
    }
    public static void main (String[] args){
        OwnerHomeCLIController ownerHomeCLIController = new OwnerHomeCLIController();
        ownerHomeCLIController.homePage();
    }
}
