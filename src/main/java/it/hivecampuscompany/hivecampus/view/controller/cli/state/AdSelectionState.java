package it.hivecampuscompany.hivecampus.view.controller.cli.state;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;

// Stato per la selezione dell'annuncio
class AdSelectionState implements ManageLeaseRequestState {
    public void handle(LeaseRequestContext context) throws InvalidSessionException {
        AdBean adBean = context.getController().manageAdSelection();
        if (adBean != null) {
            context.setAdBean(adBean);
            context.setState(new LeaseRequestViewState()); // Passa allo stato successivo
        } else context.setState(new ExitState()); // Ritorna al chiamante
    }
}

