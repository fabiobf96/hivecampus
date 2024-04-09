package it.hivecampuscompany.hivecampus.view.controller.cli.state;

import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;

// Stato per visualizzare e gestire le richieste di locazione
class LeaseRequestViewState implements State {
    public void handle(Context context) throws InvalidSessionException {
        LeaseRequestBean leaseRequestBean = context.getController().manageLeaseRequestSelection(context.getAdBean());
        if (leaseRequestBean != null) {
            context.setLeaseRequestBean(leaseRequestBean);
            context.setState(new LeaseRequestDecisionState()); // Passa allo stato decisionale
        } else {
            context.setState(new AdSelectionState()); // Torna alla selezione dell'annuncio
        }
    }
}
