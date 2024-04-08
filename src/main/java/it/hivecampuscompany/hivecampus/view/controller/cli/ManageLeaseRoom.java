package it.hivecampuscompany.hivecampus.view.controller.cli;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;
import it.hivecampuscompany.hivecampus.manager.AdManager;
import it.hivecampuscompany.hivecampus.manager.LeaseManager;
import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;

import java.io.IOException;

public class ManageLeaseRoom extends CLIController{
    public ManageLeaseRoom(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
        view = new CliGUI();
    }
    @Override
    public void homePage() {
        AdManager adManager = new AdManager();
        try {
            view.displayWelcomeMessage("manage lease request");
            AdBean adBean = selectFromList(adManager.searchProcessingAds(sessionBean), "Select an ad for load contract (or last option to go back): ");
            if (adBean == null) {
                return;
            }
            LeaseBean leaseBean = new LeaseBean(adBean, view.getStringUserInput("Contract path"));
            LeaseManager leaseManager = new LeaseManager();
            leaseManager.saveLease(sessionBean, leaseBean);
        } catch (InvalidSessionException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
