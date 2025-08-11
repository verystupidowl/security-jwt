package org.tggc.authenticationservice.exception;

public class UserNotFoundException extends BadRequestException {
    private static final String MESSAGE = "User with email %s not found";

    public UserNotFoundException(String email) {
        super(MESSAGE.concat(email));
    }

    public UserNotFoundException(long id) {
        super(String.format(MESSAGE, id));
    }
}
