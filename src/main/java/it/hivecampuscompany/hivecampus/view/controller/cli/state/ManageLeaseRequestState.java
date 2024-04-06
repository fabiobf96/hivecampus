package it.hivecampuscompany.hivecampus.view.controller.cli.state;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.bean.LeaseRequestBean;

// Interfaccia Stato
interface ManageLeaseRequestState {
    void handle(LeaseRequestContext context);
}

// Stato per la selezione dell'annuncio
class AdSelectionState implements ManageLeaseRequestState {
    public void handle(LeaseRequestContext context) {
        AdBean adBean = context.getController().selectAd();
        if (adBean != null) {
            context.setAdBean(adBean);
            context.setState(new LeaseRequestViewState()); // Passa allo stato successivo
        } else context.setState(new ExitState()); // Ritorna al chiamante
    }
}

// Stato per visualizzare e gestire le richieste di locazione
class LeaseRequestViewState implements ManageLeaseRequestState {
    public void handle(LeaseRequestContext context) {
        LeaseRequestBean leaseRequestBean = context.getController().selectLeaseRequest(context.getAdBean());
        if (leaseRequestBean != null) {
            context.setLeaseRequestBean(leaseRequestBean);
            context.setState(new LeaseRequestDecisionState()); // Passa allo stato decisionale
        } else {
            context.setState(new AdSelectionState()); // Torna alla selezione dell'annuncio
        }
    }
}

// Stato per decidere sull'accettazione o il rifiuto delle richieste
class LeaseRequestDecisionState implements ManageLeaseRequestState {
    public void handle(LeaseRequestContext context) {
        int stato = context.getController().makeDecision(context.getLeaseRequestBean()); // Metodo fittizio per decidere
        if (stato == 1) {
            context.setState(new AdSelectionState()); // Torna al primo stato dopo la decisione
        } else context.setState(new LeaseRequestViewState()); // Torna alla selezione dell'annuncio
    }
}

class ExitState implements ManageLeaseRequestState {
    public void handle(LeaseRequestContext context) {
        // Qui puoi eseguire qualsiasi operazione di pulizia necessaria prima di uscire

        // Imposta un flag o un meccanismo per segnalare l'uscita dal ciclo principale
        context.setExitRequested(true);
    }
}


