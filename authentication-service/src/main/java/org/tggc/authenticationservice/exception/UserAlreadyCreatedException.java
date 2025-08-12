package org.tggc.authenticationservice.exception;

public class UserAlreadyCreatedException extends BadRequestException {
    private static final String MESSAGE = "User with email already exists";

    public UserAlreadyCreatedException() {
        super(MESSAGE);
    }
}
