package org.tggc.authenticationservice.exception;

public class IncorrectCodeException extends BadRequestException {
    public IncorrectCodeException(String message) {
        super(message);
    }
}
