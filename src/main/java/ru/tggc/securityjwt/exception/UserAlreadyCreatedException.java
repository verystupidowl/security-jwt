package ru.tggc.securityjwt.exception;

public class UserAlreadyCreatedException extends RuntimeException {

    public UserAlreadyCreatedException(String msg) {
        super(msg);
    }
}
