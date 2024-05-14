package it.hivecampuscompany.hivecampus.state.cli.controller;

import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.manager.LeaseRequestManager;
import it.hivecampuscompany.hivecampus.model.LeaseRequestStatus;
import it.hivecampuscompany.hivecampus.view.controller.cli.CLIController;

import java.util.List;

public class ManageRequestsTenantCLIPageController extends CLIController {
    private final LeaseRequestManager manager;

    public ManageRequestsTenantCLIPageController() {
        manager = new LeaseRequestManager();
    }

    @Override
    public void homePage() {
        view.clean();
        view.displayWelcomeMessage(properties.getProperty("MANAGE_REQUEST_MSG").toUpperCase());
    }

    public int getChoice() {
        return view.getIntUserInput(properties.getProperty("CHOICE_MSG"));
    }

    public LeaseRequestBean showLeaseRequests(SessionBean sessionBean) {
        List<LeaseRequestBean> requestBeans = manager.searchTenantRequests(sessionBean);
        if (requestBeans.isEmpty()) {
            view.displayMessage(properties.getProperty("NO_REQUESTS_MSG"));
            pause();
        }
        return selectFromList(requestBeans, "requests");
    }

    public void showLeaseRequestDetails(LeaseRequestBean requestBean) {
        view.clean();
        view.displayWelcomeMessage(properties.getProperty("LEASE_REQUEST_DETAILS_MSG"));
        view.displayMessage(requestBean.getDetails());
        view.displayMessage("\n1. " + properties.getProperty("CANCEL_REQUEST_MSG"));
        view.displayMessage("2. " + properties.getProperty("GO_BACK_MSG"));
    }

    public void deleteLeaseRequest(LeaseRequestBean requestBean) {
        manager.deleteLeaseRequest(requestBean);
        view.displayMessage(properties.getProperty("REQUEST_CANCELLED_MSG"));
        pause();
    }

    public boolean checkRequestStatus(LeaseRequestBean requestBean) {
        if (requestBean.getStatus().equals(LeaseRequestStatus.PROCESSING)) {
            return true;
        }
        else {
            view.displayMessage(properties.getProperty("REQUEST_ALREADY_PROCESSED_MSG"));
            pause();
            return false;
        }
    }
}
