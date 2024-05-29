package it.hivecampuscompany.hivecampus.exception;

/**
 * Exception thrown during registration when the password and confirm password do not match.
 */
public class PasswordMismatchException extends Exception {
    public PasswordMismatchException(String msg) {
        super(msg);
    }
}
