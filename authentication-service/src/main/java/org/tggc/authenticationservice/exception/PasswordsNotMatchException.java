package org.tggc.authenticationservice.exception;

public class PasswordsNotMatchException extends BadRequestException {

    public PasswordsNotMatchException(String message) {
        super(message);
    }
}
