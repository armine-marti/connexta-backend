package org.example.phonebook.exception;
/**
 * Exception thrown when an invalid password is encountered.
 *
 * <p>Used to indicate authentication failure due to incorrect or invalid password input.
 */
public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(String message) {
        super(message);
    }
}
