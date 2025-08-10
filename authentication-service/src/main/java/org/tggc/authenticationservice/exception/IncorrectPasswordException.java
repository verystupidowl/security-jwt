package org.tggc.authenticationservice.exception;

public class IncorrectPasswordException extends RuntimeException {
    private static final String MESSAGE = "Incorrect password for email %s";

    public IncorrectPasswordException(String email) {
        super(MESSAGE.formatted(email));
    }
}
