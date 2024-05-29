package it.hivecampuscompany.hivecampus.exception;

/**
 * Exception thrown when a required field is left empty.
 */
public class EmptyFieldsException extends Exception {
    public EmptyFieldsException(String message) {
        super(message);
    }
}
