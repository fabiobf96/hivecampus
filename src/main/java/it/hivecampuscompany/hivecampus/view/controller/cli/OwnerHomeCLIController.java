package it.hivecampuscompany.hivecampus.view.controller.cli;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.view.controller.cli.state.Context;
import it.hivecampuscompany.hivecampus.view.controller.cli.state.StateCLIController;
import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;
import it.hivecampuscompany.hivecampus.view.utility.LanguageLoader;
import it.hivecampuscompany.hivecampus.bean.SessionBean;

import java.util.InputMismatchException;

public class OwnerHomeCLIController extends CLIController {
    public OwnerHomeCLIController(SessionBean sessionBean) {
        this();
        this.sessionBean = sessionBean;
    }
    public OwnerHomeCLIController(){
        view = new CliGUI();
    }
    @Override
    public void homePage() {
        properties = LanguageLoader.getLanguageProperties();
        view.displayWelcomeMessage(properties.getProperty("HOME_PAGE_MSG").toUpperCase());
        view.displayMessage("1. " + properties.getProperty("ACCOUNT_SETTINGS_MSG"));
        view.displayMessage("2. " + properties.getProperty("MANAGE_ADS_MSG"));
        view.displayMessage("3. " + properties.getProperty("MANAGE_REQUEST_MSG"));
        view.displayMessage("4. " + properties.getProperty("MANAGE_CONTRACT_MSG"));
        view.displayMessage("5. " + properties.getProperty("MANAGE_RENT_MSG"));
        view.displayMessage("6. " + properties.getProperty("EXIT_MSG"));
        CLIController controller = null;
        try {
            switch (view.getIntUserInput(properties.getProperty("CHOICE_MSG"))) {
                case 1 -> controller = new AccountSettingsCLIController();
                case 3 -> {
                    StateCLIController requestCLIController = new StateCLIController(sessionBean, StateCLIController.CLI.MANAGE_LEASE_REQUEST);
                    Context context = new Context(requestCLIController);
                    while (!context.isExitRequested()) { // Controlla se l'uscita è stata richiesta
                        context.request();
                    }
                    homePage();
                }
                case 4 -> {
                    StateCLIController leaseCLIController = new StateCLIController(sessionBean, StateCLIController.CLI.MANAGE_LEASE);
                    Context context = new Context(leaseCLIController);
                    while (!context.isExitRequested()) { // Controlla se l'uscita è stata richiesta
                        context.request();
                    }
                    homePage();
                }
                case 6 -> exit();
                default -> invalidChoice();
            }
            if (controller != null) {
                view.clean();
                controller.homePage();
            }
            homePage();
        } catch (InputMismatchException e){
            invalidChoice();
            homePage();
        } catch (InvalidSessionException e){
            view.displayMessage(e.getMessage());
        }
    }
    public static void main (String[] args){
        OwnerHomeCLIController ownerHomeCLIController = new OwnerHomeCLIController();
        ownerHomeCLIController.homePage();
    }
}
