package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageRentRatesPage;
import it.hivecampuscompany.hivecampus.state.cli.controller.ManageRentRatesOwnerCLIPageController;

public class ManageRentRatesOwnerCLIPage extends ManageRentRatesPage {
    ManageRentRatesOwnerCLIPageController controller = new ManageRentRatesOwnerCLIPageController();

    /**
     * Constructs a ManageRentRatesOwnerCLIPage object with the given context.
     *
     * @param context The context object for the owner's manage rent rates page.
     * @author Fabio Barchiesi
     */
    protected ManageRentRatesOwnerCLIPage(Context context) {
        super(context);
    }

    /**
     * Handles user interactions on the owner's manage rent rates page.
     * It displays the home page, prompts the user for input, and performs actions based on the user's choice.
     *
     * @throws InvalidSessionException if the session is invalid.
     * @author Fabio Barchiesi
     */
    @Override
    public void handle() throws InvalidSessionException {
        controller.homePage();
        controller.notImplementedYet();
        context.setState(new OwnerHomeCLIPage(context));
        context.request();
    }
}
