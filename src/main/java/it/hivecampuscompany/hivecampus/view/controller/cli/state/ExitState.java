package it.hivecampuscompany.hivecampus.view.controller.cli.state;

class ExitState implements ManageLeaseRequestState {
    public void handle(LeaseRequestContext context) {
        // Qui puoi eseguire qualsiasi operazione di pulizia necessaria prima di uscire

        // Imposta un flag o un meccanismo per segnalare l'uscita dal ciclo principale
        context.setExitRequested(true);
    }
}