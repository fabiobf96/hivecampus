package it.hivecampuscompany.hivecampus.exception;

/**
 * Exception thrown when there are issues with authentication,
 * such as incorrect email and/or password.
 */
public class AuthenticateException extends Exception {
    public AuthenticateException(String message) {
        super(message);
    }
}
