package it.hivecampuscompany.hivecampus.state;

import it.hivecampuscompany.hivecampus.exception.InvalidSessionException;

/**
 * In the context of application state management, the State interface defines various states within the application lifecycle.
 *
 * <h2>Intent</h2>
 *<ul>
 * <li>Define a contract for different states in the application.</li>
 * <li>Implements state-specific actions via the handle method.</li>
 * <li>Allows dynamic switching between states during application execution.</li>
 *</ul>
 *
 * @author Fabio Barchiesi
 */
public interface State {
    /**
     * Handles state-specific actions based on the current state of the application.
     *
     * @throws InvalidSessionException if the session is invalid.
     * @author Fabio Barchiesi
     */
    void handle() throws InvalidSessionException;
}