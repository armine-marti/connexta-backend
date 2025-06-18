package org.example.phonebook.exception;

/**
 * Exception thrown when an invalid email is encountered.
 *
 * <p>Typically used to indicate validation failure during user authentication or registration.
 */
public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String message) {
        super(message);
    }
}
