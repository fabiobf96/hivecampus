package it.hivecampuscompany.hivecampus.view.controller.cli.state;

class ExitState implements State {
    public void handle(Context context) {
        // Qui puoi eseguire qualsiasi operazione di pulizia necessaria prima di uscire

        // Imposta un flag o un meccanismo per segnalare l'uscita dal ciclo principale
        context.setExitRequested(true);
    }
}