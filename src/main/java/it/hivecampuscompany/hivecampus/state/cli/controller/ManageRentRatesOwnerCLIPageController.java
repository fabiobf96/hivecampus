package it.hivecampuscompany.hivecampus.state.cli.controller;

import it.hivecampuscompany.hivecampus.view.controller.cli.CLIController;

public class ManageRentRatesOwnerCLIPageController extends CLIController {
    @Override
    public void homePage() {
        view.displayWelcomeMessage("MANAGE RENT RATES");
    }
}