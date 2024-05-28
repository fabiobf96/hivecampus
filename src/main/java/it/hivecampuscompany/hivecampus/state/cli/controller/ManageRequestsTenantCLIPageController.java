package it.hivecampuscompany.hivecampus.state.cli.controller;

import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.manager.LeaseRequestManager;
import it.hivecampuscompany.hivecampus.model.LeaseRequestStatus;

import java.util.List;

/**
 * Controller class for the Manage Requests Tenant CLI page. It extends the CLIController class and uses the LeaseRequestManager class to manage lease requests.
 * It provides methods to display the lease requests of a tenant, show the details of a lease request, and cancel a lease request.
 */

public class ManageRequestsTenantCLIPageController extends CLIController {
    private final LeaseRequestManager manager;

    /**
     * Constructor for the ManageRequestsTenantCLIPageController class.
     */

    public ManageRequestsTenantCLIPageController() {
        manager = new LeaseRequestManager();
    }

    /**
     * Method to display the home page.
     * It displays a welcome message with "MANAGE_REQUEST_MSG" property in uppercase.
     */

    @Override
    public void homePage() {
        view.clean();
        view.displayWelcomeMessage(properties.getProperty("MANAGE_REQUEST_MSG").toUpperCase());
    }

    /**
     * Method to get the user's choice.
     * @return int value that represents the user's choice.
     */

    public int getChoice() {
        return view.getIntUserInput(properties.getProperty("CHOICE_MSG"));
    }

    /**
     * Method to show the lease requests of a tenant.
     * It gets the lease requests of a tenant and displays them.
     *
     * @param sessionBean The session bean of the user.
     * @return The LeaseRequestBean object representing the selected lease request.
     */

    public LeaseRequestBean showLeaseRequests(SessionBean sessionBean) {
        List<LeaseRequestBean> requestBeans = manager.searchTenantRequests(sessionBean);
        if (requestBeans.isEmpty()) {
            view.displayMessage(properties.getProperty("NO_REQUESTS_MSG"));
            pause();
        }
        return selectFromList(requestBeans, "requests");
    }

    /**
     * Method to show the details of a lease request.
     * It displays the details of a lease request and the options to cancel the request or go back.
     *
     * @param requestBean The LeaseRequestBean object representing the lease request.
     */

    public void showLeaseRequestDetails(LeaseRequestBean requestBean) {
        view.clean();
        view.displayWelcomeMessage(properties.getProperty("LEASE_REQUEST_DETAILS_MSG"));
        view.displayMessage(requestBean.getDetails());
        view.displayMessage("\n1. " + properties.getProperty("CANCEL_REQUEST_MSG"));
        view.displayMessage("2. " + properties.getProperty("GO_BACK_MSG"));
    }

    /**
     * Method to delete a lease request.
     * It deletes the selected lease request of a tenant.
     *
     * @param requestBean The LeaseRequestBean object representing the lease request.
     */

    public void deleteLeaseRequest(LeaseRequestBean requestBean) {
        manager.deleteLeaseRequest(requestBean);
        view.displayMessage(properties.getProperty("REQUEST_CANCELLED_MSG"));
        pause();
    }

    /**
     * Method to check the status of a lease request.
     * It checks if the lease request is still in the processing status.
     * If the lease request is already processed, it displays a message and returns false.
     *
     * @param requestBean The LeaseRequestBean object representing the lease request.
     * @return True if the lease request is still in the processing status, false otherwise.
     */

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
