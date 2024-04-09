package it.hivecampuscompany.hivecampus.view.controller.cli.state;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;

public class Context {
    private State state;
    private StateCLIController controller; // Sostituisci con il tuo tipo di view
    private AdBean adBean;
    private LeaseBean leaseBean;
    private LeaseRequestBean leaseRequestBean;
    private boolean exitRequested = false;

    public Context(StateCLIController controller) {
        this.controller = controller;
        this.state = new AdSelectionState(); // Stato iniziale
    }

    public boolean isExitRequested() {
        return exitRequested;
    }

    public void setExitRequested(boolean exitRequested) {
        this.exitRequested = exitRequested;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void request() throws InvalidSessionException {
        try {
            state.handle(this);
        } catch (InvalidSessionException e) {
            throw new InvalidSessionException();
        }
    }

    public StateCLIController getController() {
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

    public LeaseBean getLeaseBean() {
        return leaseBean;
    }

    public void setLeaseBean(LeaseBean leaseBean) {
        this.leaseBean = leaseBean;
    }
}

