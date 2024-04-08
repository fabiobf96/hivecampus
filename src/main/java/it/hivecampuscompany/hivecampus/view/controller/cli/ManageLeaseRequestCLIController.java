package it.hivecampuscompany.hivecampus.view.controller.cli;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.manager.AdManager;
import it.hivecampuscompany.hivecampus.manager.LeaseRequestManager;
import it.hivecampuscompany.hivecampus.model.AdStatus;
import it.hivecampuscompany.hivecampus.model.LeaseRequestStatus;
import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;

import java.util.List;

public class ManageLeaseRequestCLIController extends CLIController {
    private AdManager adManager;
    private LeaseRequestManager leaseRequestManager;
    private static final String INVALID_CHOICE = "Invalid choice, please try again.";

    public ManageLeaseRequestCLIController(SessionBean sessionBean) {
        view = new CliGUI();
        this.sessionBean = sessionBean;
        adManager = new AdManager();
        leaseRequestManager = new LeaseRequestManager();
    }

    @Override
    public void homePage() {
        view.clean();
        view.displayWelcomeMessage("manage lease request".toUpperCase());
    }

    public AdBean manageAdSelection() throws InvalidSessionException {
            List<AdBean> adBeanList = adManager.searchAvailableAds(sessionBean);
            adBeanList.add(null); // Aggiunge un'opzione null per rappresentare "go back"
            homePage();
            return selectFromList(adBeanList, "Select an ad for manage lease request (or last option to go back): ");
    }

    public LeaseRequestBean manageLeaseRequestSelection(AdBean adBean) throws InvalidSessionException {
            adBean.setAdStatus(AdStatus.AVAILABLE);
            List<LeaseRequestBean> leaseRequestBeanList = leaseRequestManager.searchLeaseRequestsByAd(sessionBean, adBean);
            leaseRequestBeanList.add(null); // Aggiunge un'opzione null per rappresentare "go back"
            homePage();
            return selectFromList(leaseRequestBeanList, "Select a lease request (or last option to go back): ");
    }

    public int makeDecision(LeaseRequestBean leaseRequestBean) throws InvalidSessionException {
        while (true) {
            homePage();
            view.displayMessage("Select an option (or last option to go back): ");
            view.displayMessage(leaseRequestBean.toString());
            view.displayMessage("1. " + "Accept Lease Request");
            view.displayMessage("2. " + "Reject Lease Request");
            view.displayMessage("3. " + "Go back");
            int choice = view.getIntUserInput("Choice");
            switch (choice) {
                case 1 -> leaseRequestBean.setStatus(LeaseRequestStatus.ACCEPTED);
                case 2 -> leaseRequestBean.setStatus(LeaseRequestStatus.REJECTED);
                case 3 -> {
                    return 3;
                }

                default -> {
                    view.displayMessage(INVALID_CHOICE);
                    continue;
                }
            }
            leaseRequestManager.modifyLeaseRequest(sessionBean, leaseRequestBean);
            return choice;
        }
    }
}
