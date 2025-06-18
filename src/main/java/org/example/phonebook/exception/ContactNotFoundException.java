package org.example.phonebook.exception;
/**
 * Exception thrown when a requested contact entity is not found.
 *
 * <p>Typically used to signal 404 Not Found conditions in the application.
 */
public class ContactNotFoundException extends RuntimeException {


    public ContactNotFoundException() {
    }

    public ContactNotFoundException(String message) {
        super(message);
    }

    public ContactNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContactNotFoundException(Throwable cause) {
        super(cause);
    }

    public ContactNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
