package org.tggc.authenticationservice.exception.message;

import lombok.Getter;

@Getter
public enum ExceptionMessage {
    USER_NOT_FOUND("User with email %s not found"),
    INCORRECT_CODE("Incorrect code!"),
    INCORRECT_PASSWORD("Incorrect password"),
    PASSWORDS_NOT_MATCH("Passwords not match"),
    USER_ALREADY_CREATED("User with email %s already exists"),
    USER_BLOCKED("User blocked");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }
}
