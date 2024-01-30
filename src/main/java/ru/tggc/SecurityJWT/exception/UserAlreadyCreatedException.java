package ru.tggc.SecurityJWT.exception;

public class UserAlreadyCreatedException extends RuntimeException {

    public UserAlreadyCreatedException(String msg) {
        super(msg);
    }
}
