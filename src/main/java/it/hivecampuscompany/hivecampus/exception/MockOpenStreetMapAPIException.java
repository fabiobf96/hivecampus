package it.hivecampuscompany.hivecampus.exception;

/**
 * Exception thrown when there is an issue with the mock OpenStreetMapAPI interaction.
 */
public class MockOpenStreetMapAPIException extends Exception {
    public MockOpenStreetMapAPIException(String message) {
        super(message);
    }
}
