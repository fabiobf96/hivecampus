package it.hivecampuscompany.hivecampus.state.cli.controller;

import it.hivecampuscompany.hivecampus.bean.LeaseBean;
import it.hivecampuscompany.hivecampus.view.controller.cli.CLIController;
import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;

public class ManageLeaseTenantCLIPageController extends CLIController {
    public ManageLeaseTenantCLIPageController () {
        view = new CliGUI();
    }
    @Override
    public void homePage() {
        view.clean();
        view.displayWelcomeMessage("MANAGE LEASE");
    }
    public int getChoice() {
        view.displayMessage("1. Sign Contract");
        view.displayMessage("2. View Contracts");
        view.displayMessage("3. Go back");
        return view.getIntUserInput("Choice");
    }

    public boolean manageSignContract(LeaseBean leaseBean) {
        boolean invalid = true;
        String choice;
        while (invalid) {
            view.displayMessage("do you want download the lease?");
            choice = view.getStringUserInput("Y (for yes) or N (for no)");
            if (choice.toLowerCase().contains("y")) {
                leaseBean.getContract(view.getStringUserInput("insert a path of a folder for save contract"));
                invalid = false;
            } else if (choice.toLowerCase().contains("n")) {
                invalid = false;
            } else {
                view.displayMessage("Invalid choice");
            }
        }

        while (true) {
            view.displayMessage(leaseBean.toString());
            view.displayMessage("do you want sign contract?");
            choice = view.getStringUserInput("Y (for yes) or N (for no)");
            if (choice.toLowerCase().contains("y")) {
                return true;
            } else if (choice.toLowerCase().contains("n")) {
                return false;
            } else {
                view.displayMessage("invalid choice");
            }
        }
    }
    public void successMessage() {
        view.displayMessage("la firma è avvenuta con successo");
        pause();
    }
}
