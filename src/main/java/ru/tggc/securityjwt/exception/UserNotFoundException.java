package ru.tggc.securityjwt.exception;

public class UserNotFoundException extends BadRequestException {
    private static final String MESSAGE = "User with email %s not found";

    public UserNotFoundException(String email) {
        super(MESSAGE.concat(email));
    }
}
