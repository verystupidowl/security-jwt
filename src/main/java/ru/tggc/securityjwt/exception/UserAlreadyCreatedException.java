package ru.tggc.securityjwt.exception;

public class UserAlreadyCreatedException extends BadRequestException {
    private static final String MESSAGE = "User with email %s already exists";

    public UserAlreadyCreatedException(String email) {
        super(String.format(MESSAGE, email));
    }
}
