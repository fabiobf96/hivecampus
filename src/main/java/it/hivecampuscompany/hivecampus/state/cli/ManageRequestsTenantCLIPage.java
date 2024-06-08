package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageRequestsPage;
import it.hivecampuscompany.hivecampus.state.cli.controller.ManageRequestsTenantCLIPageController;

/**
 * The ManageRequestsTenantCLIPage class represents the tenant's page for managing lease requests in the command-line interface (CLI).
 * It extends the ManageRequestsPage class and provides methods for handling user interactions on the CLI tenant's manage requests page.
 */
public class ManageRequestsTenantCLIPage extends ManageRequestsPage {

    private final ManageRequestsTenantCLIPageController controller;
    LeaseRequestBean requestBean;

    /**
     * Constructs a ManageRequestsTenantCLIPage object with the given context.
     *
     * @param context The context object for the tenant's manage requests page.
     * @author Marina Sotiropoulos
     */
    public ManageRequestsTenantCLIPage(Context context) {
        super(context);
        controller = new ManageRequestsTenantCLIPageController();
    }

    /**
     * Handles user interactions on the tenant's manage requests page.
     * It displays the home page, prompts the user for input, and performs actions based on the user's choice.
     *
     * @throws InvalidSessionException if the session is invalid.
     * @author Marina Sotiropoulos
     */
    @Override
    public void handle() throws InvalidSessionException {
        controller.homePage();
        requestBean = controller.showLeaseRequests(context.getSessionBean());
        if (requestBean == null) {
            goToTenantHomePage(new TenantHomeCLIPage(context));
        }
        else {
            // Display the lease request details
            handleRequestDetails();
        }
    }

    /**
     * Handles the lease request details and prompts the user for input
     * to cancel the lease request or return to the manage requests page.
     *
     * @author Marina Sotiropoulos
     */
    private void handleRequestDetails() {
        controller.showLeaseRequestDetails(requestBean);
        switch (controller.getChoice()) {
            case 1 -> {
                // Cancel the lease request
                controller.deleteLeaseRequest(requestBean);
                goToManageRequestsPage(new ManageRequestsTenantCLIPage(context));
            }
            case 2 -> goToManageRequestsPage(new ManageRequestsTenantCLIPage(context));
            default -> controller.invalidChoice();
        }
    }
}
