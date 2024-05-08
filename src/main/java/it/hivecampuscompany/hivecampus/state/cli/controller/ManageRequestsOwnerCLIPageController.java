package it.hivecampuscompany.hivecampus.state.cli.controller;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.view.controller.cli.CLIController;

import java.util.List;

/**
 * The ManageRequestsOwnerCLIPageController class represents a controller for managing lease requests by the owner in the command-line interface (CLI).
 * It extends the CLIController class and provides methods for displaying owner-specific options related to lease requests and collecting user input.
 */
public class ManageRequestsOwnerCLIPageController extends CLIController {

    /**
     * Overrides the homePage method to display the manage requests page for the owner.
     * This method clears the view and displays a welcome message with "MANAGE_REQUEST_MSG" property.
     */
    @Override
    public void homePage() {
        view.clean();
        view.displayWelcomeMessage(properties.getProperty("MANAGE_REQUEST_MSG"));
    }

    /**
     * Allows the owner to select an advertisement associated with lease requests.
     *
     * @param adBeanList The list of advertisement beans to choose from.
     * @return The selected AdBean object.
     */
    public AdBean selectAd(List<AdBean> adBeanList) {
        view.displayMessage(properties.getProperty("SELECT_AD_REQUEST_MSG"));
        return selectFromList(adBeanList, "");
    }

    /**
     * Allows the owner to select a lease request from the provided list.
     *
     * @param leaseRequestBeanList The list of lease request beans to choose from.
     * @return The selected LeaseRequestBean object.
     */
    public LeaseRequestBean selectRequest(List<LeaseRequestBean> leaseRequestBeanList) {
        view.displayMessage(properties.getProperty("SELECT_LEASE_MSG"));
        return selectFromList(leaseRequestBeanList, "");
    }

    /**
     * Displays the options for managing the selected advertisement and lease request.
     * It includes options to accept, reject, or go back.
     *
     * @param adBean           The selected advertisement.
     * @param leaseRequestBean The selected lease request.
     * @return The integer representing the user's choice.
     */
    public int getChoice(AdBean adBean, LeaseRequestBean leaseRequestBean) {
        view.displayMessage(adBean.toString());
        view.displayMessage(leaseRequestBean.toString());
        view.displayMessage("\n");
        view.displayMessage("1. " + properties.getProperty("ACCEPT_MSG"));
        view.displayMessage("2. " + properties.getProperty("REJECT_MSG"));
        view.displayMessage("3. " + properties.getProperty("GO_BACK_MSG"));
        return view.getIntUserInput(properties.getProperty("CHOICE_MSG"));
    }
}