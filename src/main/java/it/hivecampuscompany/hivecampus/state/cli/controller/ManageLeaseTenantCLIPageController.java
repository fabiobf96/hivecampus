package it.hivecampuscompany.hivecampus.state.cli.controller;

import it.hivecampuscompany.hivecampus.bean.LeaseBean;
import it.hivecampuscompany.hivecampus.view.controller.cli.CLIController;

/**
 * The ManageLeaseTenantCLIPageController class represents a controller for managing leases by the tenant in the command-line interface (CLI).
 * It extends the CLIController class and provides methods for displaying tenant-specific options related to lease management and collecting user input.
 */
public class ManageLeaseTenantCLIPageController extends CLIController {

    /**
     * Overrides the homePage method to display the manage lease page for the tenant.
     * This method clears the view and displays a welcome message with "MANAGE_LEASE_MSG" property in uppercase.
     */
    @Override
    public void homePage() {
        view.clean();
        view.displayWelcomeMessage(properties.getProperty("MANAGE_LEASE_MSG").toUpperCase());
    }

    /**
     * Displays the menu for lease management by the tenant and retrieves user's choice.
     *
     * @return The integer representing the user's choice.
     */
    public int getChoice() {
        view.displayMessage("1. " + properties.getProperty("SIGN_LEASE_MSG"));
        view.displayMessage("2. " + properties.getProperty("VIEW_LEASES_MSG"));
        view.displayMessage("3. " + properties.getProperty("GO_BACK_MSG"));
        return view.getIntUserInput(properties.getProperty("CHOICE_MSG"));
    }

    /**
     * Manages the process of signing a lease by the tenant.
     * This method guides the tenant through the steps of reviewing the lease details,
     * downloading the lease contract, and confirming the signing of the lease.
     *
     * @param leaseBean The LeaseBean object representing the lease to be signed.
     * @return True if the lease is successfully signed, false otherwise.
     */
    public boolean manageSignContract(LeaseBean leaseBean) {
        if (leaseBean == null) {
            view.displayMessage(properties.getProperty("NO_LEASE_MSG"));
            pause();
            return false;
        }
        boolean invalid = true;
        String choice;

        while (invalid) {
            view.displayMessage(leaseBean.toString());
            choice = view.getStringUserInput(properties.getProperty("DOWNLOAD_LEASE_MSG"));
            if (choice.equalsIgnoreCase(properties.getProperty("YES_MSG"))) {
                leaseBean.getContract(view.getStringUserInput(properties.getProperty("DOWNLOAD_LEASE_PATH_MSG")));
                invalid = false;
            } else if (choice.equalsIgnoreCase(properties.getProperty("NO_MSG"))) {
                invalid = false;
            } else {
                view.displayMessage(properties.getProperty("INVALID_OPTION_MSG"));
                pause();
            }
        }

        while (true) {
            choice = view.getStringUserInput(properties.getProperty("ASK_SIGN_LEASE_MSG"));
            if (choice.equalsIgnoreCase(properties.getProperty("YES_MSG"))) {
                return true;
            } else if (choice.equalsIgnoreCase(properties.getProperty("NO_MSG"))) {
                return false;
            } else {
                view.displayMessage(properties.getProperty("INVALID_OPTION_MSG"));
                pause();
            }
        }
    }
}