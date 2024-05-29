package it.hivecampuscompany.hivecampus.exception;

/**
 * Exception thrown when a duplicate row is encountered during a database insertion.
 */
public class DuplicateRowException extends Exception {
    public DuplicateRowException(String message) {
        super(message);
    }
}
