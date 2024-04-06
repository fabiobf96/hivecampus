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
    LeaseRequestManager leaseRequestManager;
    public ManageLeaseRequestCLIController(SessionBean sessionBean) {
        view = new CliGUI();
        this.sessionBean = sessionBean;
        adManager = new AdManager();
        leaseRequestManager = new LeaseRequestManager();
    }
    @Override
    public void homePage() {
        view.displayWelcomeMessage("manage lease request".toUpperCase());
    }

    public AdBean selectAd() {
        homePage();
        while (true) { // Ciclo per permettere all'utente di tornare indietro
            try {
                List<AdBean> adBeanList = adManager.searchAvailableAds(sessionBean);
                adBeanList.add(null); // Aggiunge un'opzione null per rappresentare "go back"
                view.displayMessage("Select an ad for manage lease request (or last option to go back): ");
                for (int i = 0; i < adBeanList.size(); i++) {
                    if (adBeanList.get(i) == null) {
                        view.displayMessage(i + ") Go back");
                    } else {
                        view.displayMessage(i + ") " + adBeanList.get(i).toString());
                    }
                }
                int choice = view.getIntUserInput("choice");
                if (choice >= 0 && choice < adBeanList.size() - 1) {
                    return adBeanList.get(choice);
                } else if (choice == adBeanList.size() - 1) {
                    return null; // Se l'utente sceglie "go back", ritorna null
                } else {
                    view.displayMessage("Invalid choice, please try again.");
                }
            } catch (InvalidSessionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public LeaseRequestBean selectLeaseRequest(AdBean adBean) {
        homePage();
        while (true) { // Simile a viewAdBean
            view.displayWelcomeMessage("MANAGE LEASE REQUEST");
            view.displayMessage("Select a lease request (or last option to go back): ");
            adBean.setAdStatus(AdStatus.AVAILABLE);
            List<LeaseRequestBean> leaseRequestBeanList = leaseRequestManager.searchLeaseRequestsByAd(adBean);
            leaseRequestBeanList.add(null); // Opzione per tornare indietro
            for (int i = 0; i < leaseRequestBeanList.size(); i++) {
                if (leaseRequestBeanList.get(i) == null) {
                    view.displayMessage(i + ") Go back");
                } else {
                    view.displayMessage(i + ") " + leaseRequestBeanList.get(i).toString());
                }
            }
            int choice = view.getIntUserInput("choice");
            if (choice >= 0 && choice < leaseRequestBeanList.size() - 1) {
                return leaseRequestBeanList.get(choice);
            } else if (choice == leaseRequestBeanList.size() - 1) {
                return null; // Permette all'utente di tornare indietro
            } else {
                view.displayMessage("Invalid choice, please try again.");
            }
        }
    }

    public int makeDecision(LeaseRequestBean leaseRequestBean) {
        while (true) {
            view.displayMessage(leaseRequestBean.toString());
            view.displayMessage("1. " + "Accept Lease Request");
            view.displayMessage("2. " + "Reject Lease Request");
            view.displayMessage("3. " + "Go back");
            int choice = view.getIntUserInput("Choice");
            switch (choice) {
                case 1 -> leaseRequestBean.setStatus(LeaseRequestStatus.ACCEPTED);
                case 2 -> leaseRequestBean.setStatus(LeaseRequestStatus.REJECTED);
                case 3 -> { return 3; }

                default -> {
                    view.displayMessage("Invalid choice, please try again.");
                    continue;}
            }
            leaseRequestManager.modifyLeaseRequest(leaseRequestBean);
            return choice;
        }
    }
}
