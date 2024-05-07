package it.hivecampuscompany.hivecampus.stateCli;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;

public interface State {
    void handle() throws InvalidSessionException;
}
