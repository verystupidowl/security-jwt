package ru.tggc.securityjwt.exception;

public class PasswordsNotMatchException extends BadRequestException {

    public PasswordsNotMatchException(String message) {
        super(message);
    }
}
