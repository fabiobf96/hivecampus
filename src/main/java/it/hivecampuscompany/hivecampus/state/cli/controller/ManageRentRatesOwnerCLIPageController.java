package it.hivecampuscompany.hivecampus.state.cli.controller;

public class ManageRentRatesOwnerCLIPageController extends CLIController {

    /**
     * Overrides the homePage method to display the manage rent rates page for the owner.
     * This method displays a welcome message with "MANAGE_RENT_RATES" property in uppercase.
     *
     * @author Fabio Barchiesi
     */
    @Override
    public void homePage() {
        view.displayWelcomeMessage("MANAGE RENT RATES");
    }
}
