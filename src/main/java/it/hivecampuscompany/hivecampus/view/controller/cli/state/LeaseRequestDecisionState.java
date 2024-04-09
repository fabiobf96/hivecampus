package it.hivecampuscompany.hivecampus.view.controller.cli.state;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;

// Stato per decidere sull'accettazione o il rifiuto delle richieste
class LeaseRequestDecisionState implements State {
    public void handle(Context context) throws InvalidSessionException {
        int choice = context.getController().makeDecision(context.getLeaseRequestBean());
        if (choice == 1) {
            context.setState(new AdSelectionState()); // Torna al primo stato dopo la decisione
        } else context.setState(new LeaseRequestViewState()); // Torna alla selezione dell'annuncio
    }
}
