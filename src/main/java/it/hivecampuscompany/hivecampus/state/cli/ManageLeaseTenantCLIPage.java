package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.exception.MockOpenAPIException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageLeasePage;
import it.hivecampuscompany.hivecampus.state.cli.controller.ManageLeaseTenantCLIPageController;

/**
 * The ManageLeaseTenantCLIPage class represents the tenant's page for managing leases in the command-line interface (CLI).
 * It extends the ManageLeasePage class and provides methods for handling user interactions on the CLI tenant's manage lease page.
 */
public class ManageLeaseTenantCLIPage extends ManageLeasePage {
    private ManageLeaseTenantCLIPageController controller = new ManageLeaseTenantCLIPageController();

    /**
     * Constructs a ManageLeaseTenantCLIPage object with the given context.
     *
     * @param context The context object for the tenant's manage lease page.
     */
    protected ManageLeaseTenantCLIPage(Context context) {
        super(context);
    }

    /**
     * Handles user interactions on the tenant's manage lease page.
     * It displays the home page, prompts the user for input, and performs actions based on the user's choice.
     *
     * @throws InvalidSessionException if the session is invalid.
     */
    @Override
    public void handle() throws InvalidSessionException {
        try {
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
        } catch (InvalidSessionException e) {
            controller.sessionExpired();
            throw new InvalidSessionException();
        }
    }

    /**
     * Handles the signing of a lease by the tenant.
     * It prompts the user to sign the lease and proceeds to sign the contract if the user agrees.
     *
     * @throws InvalidSessionException if the session is invalid.
     */
    private void sign() throws InvalidSessionException {
        controller.homePage();
        if (controller.manageSignContract(getUnSignedLease())) {
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
