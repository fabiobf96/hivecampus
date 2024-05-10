package it.hivecampuscompany.hivecampus.state;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;

/**
 * The State interface defines the contract for states in the application.
 * Implementing classes are expected to provide a handle method to perform state-specific actions.
 * This method may throw an InvalidSessionException if the session is invalid.
 */
public interface State {
    /**
     * Handles the state-specific actions.
     *
     * @throws InvalidSessionException if the session is invalid.
     */
    void handle() throws InvalidSessionException;
}