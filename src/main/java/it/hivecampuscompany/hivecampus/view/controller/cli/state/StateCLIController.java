package it.hivecampuscompany.hivecampus.view.controller.cli.state;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.manager.AdManager;
import it.hivecampuscompany.hivecampus.manager.LeaseManager;
import it.hivecampuscompany.hivecampus.manager.LeaseRequestManager;
import it.hivecampuscompany.hivecampus.model.AdStatus;
import it.hivecampuscompany.hivecampus.model.LeaseRequestStatus;
import it.hivecampuscompany.hivecampus.view.controller.cli.CLIController;
import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;

import java.io.IOException;
import java.util.List;

public class StateCLIController extends CLIController {
    private AdManager adManager;
    private LeaseRequestManager leaseRequestManager;
    private LeaseManager leaseManager;
    private static final String INVALID_CHOICE = "Invalid choice, please try again.";
    private CLI cli;

    public StateCLIController(SessionBean sessionBean, CLI cli) {
        this.cli = cli;
        view = new CliGUI();
        this.sessionBean = sessionBean;
        adManager = new AdManager();
        leaseRequestManager = new LeaseRequestManager();
        leaseManager = new LeaseManager();
    }

    @Override
    public void homePage() {
        view.clean();
        if (cli == CLI.MANAGE_LEASE_REQUEST) {
            view.displayWelcomeMessage("manage lease request".toUpperCase());
        } else view.displayWelcomeMessage("manage lease".toUpperCase());
    }

    public AdBean manageAdSelection() throws InvalidSessionException {
        List<AdBean> adBeanList;
        if (cli == CLI.MANAGE_LEASE_REQUEST) {
            adBeanList = adManager.searchAvailableAds(sessionBean);
        } else adBeanList = adManager.searchProcessingAds(sessionBean);

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

    public LeaseBean getContractPath(AdBean adBean) throws InvalidSessionException, IOException {
        homePage();
        view.displayMessage(adBean.toString());
        LeaseRequestBean leaseRequestBean = leaseRequestManager.searchLeaseRequestsByAd(sessionBean, adBean).getFirst();
        leaseRequestBean.setAdBean(adBean);
        view.displayMessage(leaseRequestBean.toString());
        view.displayMessage("insert the path for continue or notting for go back: ");
        String path = getField("Path", true);
        if (path.isBlank() || path.isEmpty()) {
            return null;
        }
        return new LeaseBean(leaseRequestBean, path);
    }
    public void loadLeaseContract (LeaseBean leaseBean) throws InvalidSessionException{
        leaseManager.loadLease(sessionBean, leaseBean);
    }
    public CLI getCli() {
        return cli;
    }

    public enum CLI {
        MANAGE_LEASE_REQUEST,
        MANAGE_LEASE
    }
}
