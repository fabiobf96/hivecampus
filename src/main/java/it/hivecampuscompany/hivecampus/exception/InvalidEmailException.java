package it.hivecampuscompany.hivecampus.exception;

/**
 * Exception thrown when an email address does not conform to the standard email format.
 */
public class InvalidEmailException extends Exception {
    public InvalidEmailException(String message) {
        super(message);
    }
}
