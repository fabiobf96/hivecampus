package it.hivecampuscompany.hivecampus.view.controller.cli.state;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;

// Interfaccia Stato
public interface ManageLeaseRequestState {
    void handle(LeaseRequestContext context) throws InvalidSessionException;
}

