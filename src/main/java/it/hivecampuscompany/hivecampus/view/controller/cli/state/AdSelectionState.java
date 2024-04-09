package it.hivecampuscompany.hivecampus.view.controller.cli.state;

import it.hivecampuscompany.hivecampus.bean.AdBean;
import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;

// Stato per la selezione dell'annuncio
class AdSelectionState implements State {
    public void handle(Context context) throws InvalidSessionException {
        StateCLIController controller = context.getController();
        AdBean adBean = controller.manageAdSelection();
        if (adBean != null) {
            context.setAdBean(adBean);
            if (controller.getCli() == StateCLIController.CLI.MANAGE_LEASE_REQUEST) {
                context.setState(new LeaseRequestViewState()); // Passa allo stato successivo
            } context.setState(new LeaseDecisionState());
        } else context.setState(new ExitState()); // Ritorna al chiamante
    }
}