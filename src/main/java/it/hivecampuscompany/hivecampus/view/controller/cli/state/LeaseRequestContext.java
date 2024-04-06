package it.hivecampuscompany.hivecampus.view.controller.cli.state;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.view.controller.cli.ManageLeaseRequestCLIController;

public class LeaseRequestContext {
    private ManageLeaseRequestState state;
    private ManageLeaseRequestCLIController controller; // Sostituisci con il tuo tipo di view
    private AdBean adBean;
    private LeaseRequestBean leaseRequestBean;
    private boolean exitRequested = false;

    public LeaseRequestContext(ManageLeaseRequestCLIController controller) {
        this.controller = controller;
        this.state = new AdSelectionState(); // Stato iniziale
    }

    public boolean isExitRequested() {
        return exitRequested;
    }

    public void setExitRequested(boolean exitRequested) {
        this.exitRequested = exitRequested;
    }

    public void setState(ManageLeaseRequestState state) {
        this.state = state;
    }

    public void request() {
        state.handle(this);
    }

    public ManageLeaseRequestCLIController getController() {
        return controller;
    }

    public AdBean getAdBean() {
        return adBean;
    }

    public void setAdBean(AdBean adBean) {
        this.adBean = adBean;
    }

    public LeaseRequestBean getLeaseRequestBean() {
        return leaseRequestBean;
    }

    public void setLeaseRequestBean(LeaseRequestBean leaseRequestBean) {
        this.leaseRequestBean = leaseRequestBean;
    }
// Metodi getter e setter per adBean, leaseRequestBean e view
}

