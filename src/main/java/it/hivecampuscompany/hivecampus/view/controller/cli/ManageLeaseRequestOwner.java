package it.hivecampuscompany.hivecampus.view.controller.cli;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.bean.SessionBean;
import it.hivecampuscompany.hivecampus.manager.LeaseRequestManager;
import it.hivecampuscompany.hivecampus.model.AdStatus;
import it.hivecampuscompany.hivecampus.view.gui.cli.CliGUI;

import java.util.List;

public class ManageLeaseRequestOwner extends CLIController {
    LeaseRequestManager leaseRequestManager;
    public ManageLeaseRequestOwner(SessionBean sessionBean){
        view = new CliGUI();
        this.sessionBean = sessionBean;
        leaseRequestManager = new LeaseRequestManager();
    }
    @Override
    public void homePage() {
        AdCLIController adCLIController = new AdCLIController(sessionBean);
        AdBean adBean = adCLIController.getAvailableAdBean();
        adBean.setAdStatus(AdStatus.AVAILABLE);
        List<LeaseRequestBean> leaseRequestBeanList = leaseRequestManager.searchLeaseRequestsByAd(adBean);
        for (LeaseRequestBean leaseRequestBean : leaseRequestBeanList) {
            view.displayMessage(leaseRequestBean.toString());
        }
    }
}
