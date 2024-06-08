package it.hivecampuscompany.hivecampus.state.cli.controller;

import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.manager.LeaseRequestManager;

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
     *
     * @author Marina Sotiropoulos
     */

    @Override
    public void homePage() {
        view.clean();
        view.displayWelcomeMessage(properties.getProperty("MANAGE_REQUEST_MSG").toUpperCase());
    }

    /**
     * Method to get the user's choice.
     * @return int value that represents the user's choice.
     * @author Marina Sotiropoulos
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
     * @author Marina Sotiropoulos
     */

    public LeaseRequestBean showLeaseRequests(SessionBean sessionBean) {
        List<LeaseRequestBean> requestBeans = manager.searchTenantRequests(sessionBean);
        if (requestBeans.isEmpty()) {
            view.displayMessage(properties.getProperty("NO_REQUESTS_FOUND_MSG"));
            pause();
        }
        return selectFromList(requestBeans, "requests");
    }

    /**
     * Method to show the details of a lease request.
     * It displays the details of a lease request and the options to cancel the request or go back.
     *
     * @param requestBean The LeaseRequestBean object representing the lease request.
     * @author Marina Sotiropoulos
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
     * Initially, it checks the status of the lease request.
     * If the lease request is still in the processing status, it deletes the lease request.
     *
     * @param requestBean The LeaseRequestBean object representing the lease request.
     * @author Marina Sotiropoulos
     */

    public void deleteLeaseRequest(LeaseRequestBean requestBean) {
        boolean res = manager.deleteLeaseRequest(requestBean);
        if (res) {
            view.displayMessage(properties.getProperty("REQUEST_CANCELLED_MSG"));
        }
        else {
            view.displayMessage(properties.getProperty("REQUEST_ALREADY_PROCESSED_MSG"));
        }
        pause();
    }
}
