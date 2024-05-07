package it.hivecampuscompany.hivecampus.stateCli.cli;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.stateCli.Context;
import it.hivecampuscompany.hivecampus.stateCli.ManageRentRatesPage;
import it.hivecampuscompany.hivecampus.stateCli.cli.controller.ManageRentRatesOwnerCLIPageController;

public class ManageRentRatesOwnerCLIPage extends ManageRentRatesPage {
    ManageRentRatesOwnerCLIPageController controller = new ManageRentRatesOwnerCLIPageController();

    protected ManageRentRatesOwnerCLIPage(Context context) {
        super(context);
    }

    @Override
    public void handle() throws InvalidSessionException {
        controller.homePage();
        controller.notImplementedYet();
        context.setState(new OwnerHomeCLIPage(context));
        context.request();
    }
}
