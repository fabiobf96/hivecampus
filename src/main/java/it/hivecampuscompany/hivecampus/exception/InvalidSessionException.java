package it.hivecampuscompany.hivecampus.exception;

/**
 * Exception thrown when a session is invalid.
 */
public class InvalidSessionException extends Exception {
    public InvalidSessionException() {
        super("Invalid session");
    }
}
