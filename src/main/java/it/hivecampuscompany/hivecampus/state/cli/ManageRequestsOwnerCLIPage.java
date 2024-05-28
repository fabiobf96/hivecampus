package it.hivecampuscompany.hivecampus.state.cli;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.model.LeaseRequestStatus;
import it.hivecampuscompany.hivecampus.state.Context;
import it.hivecampuscompany.hivecampus.state.ManageRequestsPage;
import it.hivecampuscompany.hivecampus.state.cli.controller.ManageRequestsOwnerCLIPageController;

import java.util.List;

/**
 * The ManageRequestsOwnerCLIPage class represents the owner's page for managing lease requests in the command-line interface (CLI).
 * It extends the ManageRequestsPage class and provides methods for handling user interactions on the CLI owner's manage requests page.
 */
public class ManageRequestsOwnerCLIPage extends ManageRequestsPage {
    private AdBean adBean;
    private LeaseRequestBean leaseRequestBean;
    private final ManageRequestsOwnerCLIPageController controller;

    /**
     * Constructs a ManageRequestsOwnerCLIPage object with the given context.
     *
     * @param context The context object for the owner's manage requests page.
     * author Fabio Barchiesi
     */
    public ManageRequestsOwnerCLIPage(Context context) {
        super(context);
        controller = new ManageRequestsOwnerCLIPageController();
    }

    /**
     * Handles user interactions on the owner's manage requests page.
     * It displays the home page, prompts the user for input, and performs actions based on the user's choice.
     *
     * @throws InvalidSessionException if the session is invalid.
     * author Fabio Barchiesi
     */
    @Override
    public void handle() throws InvalidSessionException {
        try {
            controller.homePage();
            adBean = controller.selectAd(retrieveAvailableAds());
            if (adBean != null) {
                selectLeaseRequest();
            } else {
                goToOwnerHomePage(new OwnerHomeCLIPage(context));
                context.request();
            }
        } catch (InvalidSessionException e) {
            controller.sessionExpired();
            throw new InvalidSessionException();
        }
    }

    /**
     * Prompts the owner to select a lease request.
     * It retrieves the lease requests associated with the selected ad and prompts the owner to select one for decision-making.
     *
     * @throws InvalidSessionException if the session is invalid.
     * author Fabio Barchiesi
     */
    private void selectLeaseRequest() throws InvalidSessionException {
        controller.homePage();
        List<LeaseRequestBean> leaseRequestBeanList = retrieveLeaseRequests(adBean);
        leaseRequestBean = controller.selectRequest(leaseRequestBeanList);
        if (leaseRequestBean != null) {
            makeDecision();
        } else {
            context.request();
        }
    }

    /**
     * Handles the decision-making for the selected lease request.
     * It prompts the owner to accept, reject, or go back to select another lease request.
     *
     * @throws InvalidSessionException if the session is invalid.
     * author Fabio Barchiesi
     */
    private void makeDecision() throws InvalidSessionException {
        controller.homePage();
        int choice = controller.getChoice(adBean, leaseRequestBean);
        switch (choice) {
            case 1 -> {
                leaseRequestBean.setStatus(LeaseRequestStatus.ACCEPTED);
                updateLeaseRequest(leaseRequestBean);
                controller.successMessage("ACCEPT_REQUEST");
                context.request();
            }
            case 2 -> {
                leaseRequestBean.setStatus(LeaseRequestStatus.REJECTED);
                updateLeaseRequest(leaseRequestBean);
                selectLeaseRequest();
            }
            case 3 -> selectLeaseRequest();
            default -> {
                controller.invalidChoice();
                makeDecision();
            }
        }
    }
}