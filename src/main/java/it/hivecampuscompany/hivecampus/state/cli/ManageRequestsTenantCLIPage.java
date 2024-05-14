package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageRequestsPage;
import it.hivecampuscompany.hivecampus.state.cli.controller.ManageRequestsTenantCLIPageController;

public class ManageRequestsTenantCLIPage extends ManageRequestsPage {

    private final ManageRequestsTenantCLIPageController controller;
    LeaseRequestBean requestBean;

    public ManageRequestsTenantCLIPage(Context context) {
        super(context);
        controller = new ManageRequestsTenantCLIPageController();
    }

    @Override
    public void handle() throws InvalidSessionException {
        controller.homePage();
        requestBean = controller.showLeaseRequests(context.getSessionBean());
        if (requestBean == null) {
            goToTenantHomePage(new TenantHomeCLIPage(context));
        }
        else {
            // Display the lease request details
            if (controller.checkRequestStatus(requestBean)){
                handleRequestDetails();
            }
            else goToManageRequestsPage(new ManageRequestsTenantCLIPage(context));
        }
    }

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
