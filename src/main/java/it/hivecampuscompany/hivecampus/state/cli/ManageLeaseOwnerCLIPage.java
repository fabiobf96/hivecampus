package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageLeasePage;
import it.hivecampuscompany.hivecampus.state.cli.controller.ManageLeaseOwnerCLIPageController;

/**
 * The ManageLeaseOwnerCLIPage class represents the owner's page for managing leases in the command-line interface (CLI).
 * It extends the ManageLeasePage class and provides methods for handling user interactions on the CLI owner's manage lease page.
 */
public class ManageLeaseOwnerCLIPage extends ManageLeasePage {
    private ManageLeaseOwnerCLIPageController controller;
    private AdBean adBean;

    /**
     * Constructs a ManageLeaseOwnerCLIPage object with the given context.
     *
     * @param context The context object for the owner's manage lease page.
     */
    public ManageLeaseOwnerCLIPage(Context context) {
        super(context);
        controller = new ManageLeaseOwnerCLIPageController();
    }

    /**
     * Handles user interactions on the owner's manage lease page.
     * It displays the home page, prompts the user for input, and performs actions based on the user's choice.
     *
     * @throws InvalidSessionException if the session is invalid.
     */
    @Override
    public void handle() throws InvalidSessionException {
        try {
            controller.homePage();
            switch (controller.getChoice()) {
                case 1 -> choiceAd();
                case 2 -> {
                    controller.notImplementedYet();
                    context.request();
                }
                case 3 -> goToOwnerHomePage(new OwnerHomeCLIPage(context));
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
     * Handles the user's choice of ad for lease management.
     * It prompts the user to select an ad and proceeds to create a new lease if an ad is chosen.
     *
     * @throws InvalidSessionException if the session is invalid.
     */
    private void choiceAd() throws InvalidSessionException {
        controller.homePage();
        adBean = controller.selectAd(getProcessingAds());
        if (adBean != null) {
            createNewLease();
        } else {
            context.request();
        }
    }

    /**
     * Creates a new lease for the selected ad.
     * It prompts the user for lease information and uploads the lease to the system.
     *
     * @throws InvalidSessionException if the session is invalid.
     */
    private void createNewLease() throws InvalidSessionException {
        controller.homePage();
        LeaseBean leaseBean = controller.getLease(adBean, getLeaseRequestInformation(adBean));
        if (leaseBean != null) {
            uploadLease(leaseBean);
            controller.successMessage("LOADED");
        } else {
            choiceAd();
        }
    }
}