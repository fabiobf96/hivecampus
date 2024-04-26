package it.hivecampuscompany.hivecampus.state;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;

public interface State {
    void handle() throws InvalidSessionException;
}
