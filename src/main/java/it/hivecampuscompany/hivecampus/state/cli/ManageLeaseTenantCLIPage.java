package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.exception.MockOpenAPIException;
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
            case 1 -> sign();
            case 2 -> controller.notImplementedYet();
            case 3 -> goToTenantHomePage(new TenantHomeCLIPage(context));
            default -> {
                controller.invalidChoice();
                context.request();
            }
        }
    }

    private void sign() throws InvalidSessionException {
        controller.homePage();
        if (controller.manageSignContract(getUnSignedLease())){
            try {
                signContract();
                controller.successMessage();
                goToTenantHomePage(new TenantHomeCLIPage(context));
            } catch (MockOpenAPIException e) {
                controller.displayError(e.getMessage());
            }
        }
    }
}
