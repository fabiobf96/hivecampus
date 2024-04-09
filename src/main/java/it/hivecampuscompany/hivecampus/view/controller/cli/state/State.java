package it.hivecampuscompany.hivecampus.view.controller.cli.state;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;

// Interfaccia Stato
public interface State {
    void handle(Context context) throws InvalidSessionException;
}

