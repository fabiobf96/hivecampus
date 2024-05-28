package it.hivecampuscompany.hivecampus.exception;

/**
 * Exception thrown when there is an issue with the mock OpenAPI interaction.
 */
public class MockOpenAPIException extends Exception {
    public MockOpenAPIException(String message) {
        super(message);
    }
}
