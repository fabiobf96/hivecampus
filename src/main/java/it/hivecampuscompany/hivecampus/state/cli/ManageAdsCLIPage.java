package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageAdsPage;
import it.hivecampuscompany.hivecampus.state.cli.controller.InitialCLIPageController;

public class ManageAdsCLIPage extends ManageAdsPage {
    // controlle provvisorio
    InitialCLIPageController controller;
    protected ManageAdsCLIPage(Context context) {
        super(context);
        controller = new InitialCLIPageController();
    }

    @Override
    public void handle() throws InvalidSessionException {
        controller.notImplementedYet();
        goToOwnerHomePage(new OwnerHomeCLIPage(context));
        context.request();
    }
}
