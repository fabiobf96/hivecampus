package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageLeasePage;
import it.hivecampuscompany.hivecampus.state.cli.controller.ManageLeaseTenantCLIPageController;

public class ManageLeaseTenantCLIPage extends ManageLeasePage {
    ManageLeaseTenantCLIPageController controller = new ManageLeaseTenantCLIPageController();
    protected ManageLeaseTenantCLIPage(Context context) {
        super(context);
    }

    @Override
    public void handle() throws InvalidSessionException {
        controller.homePage();
        switch (controller.getChoice()) {
            case 1 -> signContract();
            case 2 -> controller.notImplementedYet();
            case 3 -> goToTenantHomePage(new TenantHomeCLIPage(context));
        }
    }

    private void signContract() throws InvalidSessionException {
        controller.homePage();
        if (controller.manageSignContract(getUnSignedLease())){

        }
    }
}
