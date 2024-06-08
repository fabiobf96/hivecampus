package it.hivecampuscompany.hivecampus.state.cli.controller;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseContractBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.exception.InvalidExtentionException;

import java.io.IOException;
import java.util.List;

/**
 * The ManageLeaseOwnerCLIPageController class represents a controller for managing leases by the owner in the command-line interface (CLI).
 * It extends the CLIController class and provides methods for displaying lease management options and collecting user input.
 */
public class ManageLeaseOwnerCLIPageController extends CLIController {

    /**
     * Overrides the homePage method to display the manage lease page for the owner.
     * This method clears the view and displays a welcome message with "MANAGE_LEASE_MSG" property in uppercase.
     *
     * @author Fabio Barchiesi
     */
    @Override
    public void homePage() {
        view.clean();
        view.displayWelcomeMessage(properties.getProperty("MANAGE_LEASE_MSG").toUpperCase());
    }

    /**
     * Displays the menu for lease management by the owner and retrieves user's choice.
     *
     * @return The integer representing the user's choice.
     * @author Fabio Barchiesi
     */
    public int getChoice() {
        view.displayMessage("1. " + properties.getProperty("UPLOAD_LEASE_MSG"));
        view.displayMessage("2. " + properties.getProperty("CANCEL_LEASE_MSG"));
        view.displayMessage("3. " + properties.getProperty("GO_BACK_MSG"));
        return view.getIntUserInput(properties.getProperty("CHOICE_MSG"));
    }

    /**
     * Allows the owner to select an advertisement for lease from a list.
     *
     * @param adBeanList The list of advertisement beans to choose from.
     * @return The selected AdBean object.
     * @author Fabio Barchiesi
     */
    public AdBean selectAd(List<AdBean> adBeanList) {
        view.displayMessage(properties.getProperty("SELECT_AD_LEASE_MSG"));
        return selectFromList(adBeanList, "");
    }

    /**
     * Retrieves lease details from the owner for the selected advertisement.
     *
     * @param adBean            The selected advertisement.
     * @param leaseRequestBean  The lease request bean containing necessary information.
     * @return The LeaseBean object representing the lease.
     * @author Fabio Barchiesi
     */
    public LeaseContractBean getLease(AdBean adBean, LeaseRequestBean leaseRequestBean) {
        while (true) {
            try {
                view.displayMessage(properties.getProperty("GET_LEASE_PATH_MSG"));
                view.displayMessage(adBean.toString());
                view.displayMessage(leaseRequestBean.toString());

                String path = getField(properties.getProperty("PATH_MSG"), true);
                if (path.isBlank()) {
                    return null;
                }
                leaseRequestBean.setAdBean(adBean);
                return new LeaseContractBean(leaseRequestBean, path);
            } catch (IOException e) {
                displayError("ERROR_PATH_MSG");
            } catch (InvalidExtentionException e) {
                displayError(e.getMessage());
            }
        }
    }
}